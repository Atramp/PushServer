package com.teradata.pushserver.service;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.teradata.common.Configuration;
import com.teradata.common.service.AbstractTransactionService;
import com.teradata.pushserver.bean.message.BDPushMessage;
import com.teradata.pushserver.bean.message.BDPushNotification;
import com.teradata.pushserver.bean.pushbind.BDPushBind;
import com.teradata.pushserver.bean.pushlog.PushLog;
import com.teradata.pushserver.controller.PushController;
import com.teradata.pushserver.dao.interfaces.PushLogDao;
import com.teradata.pushserver.service.interfaces.PushBindService;
import com.teradata.pushserver.service.interfaces.PushService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by alex on 15-3-31.
 */
public class BDPushService extends AbstractTransactionService implements PushService<BDPushMessage> {
    private static Log logger = LogFactory.getLog(BDPushService.class);

    private final Map<String, BaiduPushClient> clients = new HashMap();

    @Autowired
    private PushLogDao pushLogDao;

    @Autowired
    private PushBindService<BDPushBind> pushBindService;

    private BaiduPushClient getClient(String appID) {
        if (clients.isEmpty())
            synchronized (clients) {
                if (clients.isEmpty()) {
                    Map keyPairs = Configuration.get("KEY_PAIR");
                    if (keyPairs != null) {
                        for (Map.Entry entry : (Set<Map.Entry>) keyPairs.entrySet()) {
                            String str = entry.getValue().toString();
                            String[] temp = str.split(",");
                            BaiduPushClient client = new BaiduPushClient(new PushKeyPair(temp[0], temp[1]));
                            client.setChannelLogHandler(new YunLogHandler() {
                                public void onHandle(YunLogEvent event) {
                                    logger.info(event.getMessage());
                                }
                            });
                            clients.put(StringUtils.substringAfterLast(entry.getKey().toString(), "_"), client);
                        }
                    }
                }
            }
        return clients.get(appID);
    }


    @Override
    public boolean pushAll(String deviceType, BDPushMessage message) {
        String appID = PushController.appID.get();
        if (StringUtils.isEmpty(appID))
            return false;
        BaiduPushClient baiduPushClient = getClient(appID);
        PushMsgToAllRequest request = new PushMsgToAllRequest();
        request.setMessageType(message.getMessageType());
        request.setMessage(message.generate());
        if (deviceType != null)
            request.setDeviceType(Integer.valueOf(deviceType));
        PushLog pushLog = new PushLog(appID, message.getMessageType(), PushLog.TARGET_ALL, request.getMessage());
        try {
            PushMsgToAllResponse response = baiduPushClient.pushMsgToAll(request);
            pushLog.setMsgID(response.getMsgId());
            pushLog.setResult(PushLog.RESULT.SUCCESS);
            return true;
        } catch (PushClientException pce) {
            pushLog.setResult(PushLog.RESULT.ERROR);
            pushLog.setError(pce.getMessage());
            pce.printStackTrace();
        } catch (PushServerException pse) {
            String error = String.format("requestId: %d, errorCode: %d, errorMsg: %s",
                    pse.getRequestId(), pse.getErrorCode(), pse.getErrorMsg());
            pushLog.setResult(PushLog.RESULT.ERROR);
            pushLog.setError(error);
            System.out.println(error);
        } finally {
            pushLogDao.insertLog(pushLog);
        }
        return false;
    }

    @Override
    public boolean pushByUser(String username, BDPushMessage message) {
        boolean result = true;
        List<BDPushBind> binds = pushBindService.getUserPushBinds(username);
        if (binds == null || binds.isEmpty())
            return false;
        String appID = PushController.appID.get();
        if (StringUtils.isEmpty(appID))
            return false;
        BaiduPushClient baiduPushClient = getClient(appID);
        PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest();
        request.setMessageType(message.getMessageType());
        for (BDPushBind bind : binds) {
            // ios端没有消息
            if (message.getClass() == BDPushMessage.class && bind.getDeviceType() == BDPushBind.DEVICE_TYPE.IOS.getType())
                continue;
            else
                request.setMessage(message.generate());
            request.setChannelId(bind.getRemoteUserID());
            request.setDeviceType(bind.getDeviceType());
            PushLog pushLog = new PushLog(appID, message.getMessageType(), bind.getRemoteUserID(), message.generate());
            try {
                PushMsgToSingleDeviceResponse response = baiduPushClient.pushMsgToSingleDevice(request);
                pushLog.setMsgID(response.getMsgId());
                pushLog.setResult(PushLog.RESULT.SUCCESS);
            } catch (PushClientException pce) {
                pushLog.setResult(PushLog.RESULT.ERROR);
                pushLog.setError(pce.getMessage());
                pce.printStackTrace();
                result = false;
            } catch (PushServerException pse) {
                String error = String.format("requestId: %d, errorCode: %d, errorMsg: %s",
                        pse.getRequestId(), pse.getErrorCode(), pse.getErrorMsg());
                pushLog.setResult(PushLog.RESULT.ERROR);
                pushLog.setError(error);
                result = false;
            } finally {
                pushLogDao.insertLog(pushLog);
            }
        }
        return result;
    }

}

package com.teradata.pushserver.service;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushAsyncClient;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.*;
import com.teradata.pushserver.bean.message.PushMessageBean;
import com.teradata.pushserver.bean.pushbind.BDPushBindBean;
import com.teradata.pushserver.service.interfaces.PushBindService;
import com.teradata.pushserver.service.interfaces.PushMessageService;
import com.teradata.pushserver.service.interfaces.PushService;
import com.teradata.pushserver.util.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

import java.util.List;

/**
 * Created by alex on 15-3-31.
 */
public class BDPushService implements PushService {
    // 百度推送接口中的消息类型
    public enum TYPE {
        MESSAGE(0), NOTIFICATION(1);
        private int type;

        private TYPE(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        @Override
        public String toString() {
            return String.valueOf(this.type);
        }
    }
    Logger logger = LoggerFactory.getLogger(BDPushService.class);

    private final PushKeyPair keyPair = new PushKeyPair(Configuration.get("BAIDU_PUSH_API_KEY"), Configuration.get("BAIDU_PUSH_SECRET_KEY"));

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private PushBindService<BDPushBindBean> pushBindService;

    @Autowired
    private ResourceTransactionManager transactionManager;

    @Override
    public boolean pushAll(PushMessageBean pushMessageBean) {
        // 使用spring管理事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 将消息存入数据库
            boolean messageStored = pushMessageService.addMessageToAllUser(pushMessageBean);
            // 将用户-消息关系存入数据库

            boolean result = messageStored && pushBroadCast(pushMessageBean, null);
            if (result)
                transactionManager.commit(status);
            return result;
        } catch (Exception ex) {
            transactionManager.rollback(status);
        }
        return false;
    }

    @Override
    public int pushByClient(String device_type, PushMessageBean pushMessageBean) {
        return -1;
    }

    @Override
    public boolean pushByUser(String username, PushMessageBean pushMessageBean) {
        // 查询用户所有绑定，推送到该用户绑定的所有手机
        List<BDPushBindBean> userBinds = pushBindService.queryPushBindsByUser(username);
        int isSuccess = userBinds.size();
        // 使用spring管理事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 将推送消息存储到数据库
            if (!pushMessageService.addMessage(pushMessageBean))
                return false;

            // 将推送消息-用户绑定关系存储到数据库
            if (!pushMessageService.addMessageToUser(username, pushMessageBean))
                return false;
            for (BDPushBindBean pushBindBean : userBinds)
                if (pushUnicast(pushBindBean, pushMessageBean))
                    isSuccess--;
            // 任意一个终端推送成功，即提交事务
            if (isSuccess < userBinds.size())
                transactionManager.commit(status);
            else
                transactionManager.rollback(status);
        } catch (Exception ex) {
            transactionManager.rollback(status);
            ex.printStackTrace();
        }
        return isSuccess < userBinds.size();
    }

    /**
     * 群推m
     *
     * @param pushMessageBean
     * @param device_type
     * @return
     * @throws PushClientException
     * @throws PushServerException
     */
    private boolean pushBroadCast(PushMessageBean pushMessageBean, String device_type) throws PushClientException, PushServerException {
        BaiduPushClient baiduPushClient = new BaiduPushClient(keyPair,"111.13.12.61");
        baiduPushClient.setChannelLogHandler(new YunLogHandler() {
            public void onHandle(YunLogEvent event) {
                logger.info(event.getMessage());
            }
        });
        PushMsgToAllRequest request = new PushMsgToAllRequest();
        request.setMessageType(Integer.valueOf(pushMessageBean.getMessage_type()));
        request.setMessage(pushMessageBean.generate());
        if (device_type != null && !device_type.isEmpty())
            request.setDeviceType(Integer.valueOf(device_type));
        PushMsgToAllResponse response = baiduPushClient.pushMsgToAll(request);
        String msgId = response.getMsgId();
        return msgId != null && msgId.isEmpty();
    }

    /**
     * 单推
     *
     * @param pushBindBean
     * @param pushMessageBean
     * @return
     */
    private boolean pushUnicast(BDPushBindBean pushBindBean, PushMessageBean pushMessageBean) throws PushClientException, PushServerException {
        BaiduPushClient baiduPushClient = new BaiduPushClient(keyPair, "111.13.12.61");
        baiduPushClient.setChannelLogHandler(new YunLogHandler() {
            public void onHandle(YunLogEvent event) {
                logger.info(event.getMessage());
            }
        });
        PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest();
        request.setChannelId(pushBindBean.getBaidu_channel_id());
        // 安卓推送类型为消息，IOS为通知
        if (BDPushBindBean.DEVICE_TYPE.ANDROID.toString().equals(pushBindBean.getDevice_type()))
            request.setMessageType(TYPE.MESSAGE.getType());
        else
            request.setMessageType(TYPE.NOTIFICATION.getType());
        request.setMessage(pushMessageBean.generate());
        request.setDeviceType(Integer.valueOf(pushBindBean.getDevice_type()));
        PushMsgToSingleDeviceResponse response = baiduPushClient.pushMsgToSingleDevice(request);
        String msgId = response.getMsgId();
        return msgId != null && !msgId.isEmpty();
    }

    private boolean pushBatchUnicast() {
        return false;
    }

}

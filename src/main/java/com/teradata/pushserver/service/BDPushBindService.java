package com.teradata.pushserver.service;

import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushAsyncClient;
import com.baidu.yun.push.client.BaiduPushClient;
import com.teradata.pushserver.bean.pushbind.BDPushBind;
import com.teradata.pushserver.dao.interfaces.PushBindDao;
import com.teradata.common.service.AbstractTransactionService;
import com.teradata.pushserver.service.interfaces.PushBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

/**
 * Created by alex on 15-3-26.
 */
public class BDPushBindService extends AbstractTransactionService implements PushBindService<BDPushBind> {
    @Autowired
    private PushBindDao pushBindDao;

    @Override
    public List<BDPushBind> getAllPushBinds() {
        return pushBindDao.queryPushBinds();
    }

    @Override
    public List<BDPushBind> getUserPushBinds(String username) {
        return pushBindDao.queryPushBindsByUser(username);
    }

    @Override
    public boolean bind(BDPushBind bean) {
        TransactionStatus status = beginTransaction();
        boolean result = false;
        try {
            result = pushBindDao.updatePushBind(bean) == 1;
            // 未更新成功则表中没有，新增一条
            if (!result)
                result = pushBindDao.insertPushBind(bean) == 1;
            commit(status);
        } catch (Exception e) {
            rollback(status);
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public boolean unbind(BDPushBind bean) {
        return pushBindDao.deletePushBind(bean) == 1;
    }

}

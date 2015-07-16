package com.teradata.pushserver.service;

import com.teradata.pushserver.bean.pushbind.BDPushBindBean;
import com.teradata.pushserver.dao.interfaces.PushBindDao;
import com.teradata.pushserver.service.interfaces.PushBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.ResourceTransactionManager;

import java.util.List;

/**
 * Created by alex on 15-3-26.
 */
public class BDPushBindService implements PushBindService<BDPushBindBean> {
    @Autowired
    private PushBindDao pushBindDao;

    @Autowired
    private ResourceTransactionManager transactionManager;

    public List<BDPushBindBean> getAllPushBinds() {
        return pushBindDao.queryPushBinds();
    }

    @Override
    public List<BDPushBindBean> getPushBindFromBaidu() {
        return null;
    }


    @Override
    public List<BDPushBindBean> queryPushBindsByUser(String username) {
        return pushBindDao.queryPushBindsByUser(username);
    }

    @Override
    public boolean bind(BDPushBindBean bean) {
        boolean result = true;
        // 未更新成功则表中没有该baidu_user_id和baidu_channel_id的关系，新增一条
        if (pushBindDao.updatePushBind(bean) == 0)
            result = pushBindDao.insertPushBind(bean) == 1;
        return result;
    }

    @Override
    public boolean unbind(BDPushBindBean bean) {
        return pushBindDao.deletePushBind(bean) == 1;
    }

}

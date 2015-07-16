package com.teradata.pushserver.service;

import com.teradata.pushserver.bean.message.PushMessageBean;
import com.teradata.pushserver.bean.message.UserPushMessageBean;
import com.teradata.pushserver.service.interfaces.PushMessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Create by alex on 15-3-26.
 */
public class BDPushMessageService implements PushMessageService {
    @Autowired
    private com.teradata.pushserver.dao.BDPushMessageDao BDPushMessageDao;

    public List<PushMessageBean> getAllPushMessages() {
        return BDPushMessageDao.queryAllMessage();
    }

    public PushMessageBean getMessageByID(String id) {
        return BDPushMessageDao.queryMessageByID(id);
    }

    public List<UserPushMessageBean> getMessagesByUser(String username) {
        return BDPushMessageDao.queryMessageByUser(username);
    }

    public boolean updateMessageStatus(String username, String messageID, String status) {
        return BDPushMessageDao.updateMessageStatus(username, messageID, status) == 1;
    }

    public boolean addMessageToUser(String username, String messageID) {
        UserPushMessageBean userPushMessageBean = new UserPushMessageBean();
        userPushMessageBean.setUsername(username);
        userPushMessageBean.setId(messageID);
        userPushMessageBean.setStatus(UserPushMessageBean.STATUS.UNREAD.toString());
        return BDPushMessageDao.insertUserMessage(userPushMessageBean) == 1;
    }

    @Override
    public boolean addMessageToUser(String username, PushMessageBean pushMessageBean) {
        return addMessageToUser(username, pushMessageBean.getId());
    }

    @Override
    public boolean addMessageToAllUser(String messageID) {
        UserPushMessageBean userPushMessageBean = new UserPushMessageBean();
        userPushMessageBean.setId(messageID);
        userPushMessageBean.setStatus(UserPushMessageBean.STATUS.UNREAD.toString());
        return BDPushMessageDao.insertMessageToAllUser(userPushMessageBean) > 0;
    }

    @Override
    public boolean addMessageToAllUser(PushMessageBean pushMessageBean) {
        return addMessageToAllUser(pushMessageBean.getId());
    }

    public boolean addMessage(PushMessageBean bean) {
        bean.generate();
        return BDPushMessageDao.insertMessage(bean) == 1;
    }

}

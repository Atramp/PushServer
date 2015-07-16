package com.teradata.pushserver.service.interfaces;

import com.teradata.pushserver.bean.message.PushMessageBean;
import com.teradata.pushserver.bean.message.UserPushMessageBean;

import java.util.List;

/**
 * Create by alex on 15-3-26.
 */
public interface PushMessageService {

    public List<PushMessageBean> getAllPushMessages();

    public PushMessageBean getMessageByID(String id);

    public List<UserPushMessageBean> getMessagesByUser(String username);

    public boolean updateMessageStatus(String username, String messageID, String status);

    public boolean addMessageToUser(String username, String messageID);

    public boolean addMessageToUser(String username, PushMessageBean pushMessageBean);

    public boolean addMessageToAllUser(String messageID);

    public boolean addMessageToAllUser(PushMessageBean pushMessageBean);

    public boolean addMessage(PushMessageBean bean);

}

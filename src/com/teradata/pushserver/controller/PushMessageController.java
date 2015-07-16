package com.teradata.pushserver.controller;

import com.teradata.pushserver.bean.message.PushMessageBean;
import com.teradata.pushserver.bean.message.UserPushMessageBean;
import com.teradata.pushserver.service.BDPushMessageService;
import com.teradata.pushserver.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by alex on 15-3-24.
 */
@RestController
@RequestMapping(value = "/message")
public class PushMessageController {
    private static final Logger logger = LoggerFactory.getLogger(PushMessageController.class);

    @Autowired
    private BDPushMessageService service;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<PushMessageBean> query() {
        return service.getAllPushMessages();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PushMessageBean queryByID(String id) {
        return service.getMessageByID(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public boolean addMessage(String type, String title, String description, String url) {
        PushMessageBean message = new PushMessageBean(type, title, description, url);
        return service.addMessage(message);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public List<UserPushMessageBean> queryByUser(String username) {
        return service.getMessagesByUser(username);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateMessageStatus(String username, String message_id) {
        return service.updateMessageStatus(username, message_id, UserPushMessageBean.STATUS.READ.toString());
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseBody
    public boolean addMessageToUser(String username, String message_id) {
        return service.updateMessageStatus(username, message_id, UserPushMessageBean.STATUS.UNREAD.toString());
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteUserMessage(String username, String message_id) {
        return service.updateMessageStatus(username, message_id, UserPushMessageBean.STATUS.DELETED.toString());
    }
}

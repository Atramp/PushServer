package com.teradata.pushserver.controller;

import com.teradata.pushserver.bean.pushbind.BDPushBind;
import com.teradata.pushserver.service.interfaces.PushBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by alex on 15-3-24.
 */
@RestController
@RequestMapping(value = "/bind")
public class PushBindController {
    @Autowired
    private PushBindService service;

    /**
     * 查询所有绑定信息
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List query() {
        return service.getAllPushBinds();
    }

    /**
     * 根据user_id从百度查询绑定关系
     *
     * @param user_id
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public List query(String user_id) {
        return null;
    }

    /**
     * 绑定一个推送绑定关系。根据user_id和channel_id更新username，更新失败则插入一条。
     * 掌上经分用户登录后app会与百度推送服务器绑定，因为百度推送根据终端分配user_id和channel_id。
     *
     * @param username
     * @param remote_user_id
     * @param device_type
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public boolean bind(String username, String remote_user_id, int device_type) {
        BDPushBind bean = new BDPushBind();
        bean.setUsername(username);
        bean.setRemoteUserID(remote_user_id);
        bean.setDeviceType(device_type);
        return service.bind(bean);
    }

    /**
     * 删除一个推送绑定关系
     *
     * @param username
     * @param channel_id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public boolean unbind(String username, String channel_id) {
        BDPushBind bean = new BDPushBind();
        bean.setUsername(username);
        bean.setRemoteUserID(channel_id);
        return service.unbind(bean);
    }

}

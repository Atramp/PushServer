package com.teradata.pushserver.controller;

import com.teradata.pushserver.bean.message.PushMessageBean;
import com.teradata.pushserver.service.interfaces.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alex on 15-3-27.
 * <p/>
 * 推送接口
 */

@RestController
@RequestMapping("/push")
public class PushController {
    @Autowired
    private PushService pushService;

    /**
     * 推送给所有用户
     *
     * @param title
     * @param description
     * @param url
     * @return
     */
    @RequestMapping("/all")
    @ResponseBody
    public boolean pushAll(String title, String description, String url) {
        PushMessageBean pushMessageBean = new PushMessageBean(PushMessageBean.CUSTOM_MESSAGE_TYPE.MEDIA.getType(), title, description, url);
        return pushService.pushAll(pushMessageBean);
    }

    /**
     * 推送给指定终端
     *
     * @param device_type
     * @param title
     * @param description
     * @param url
     * @return
     */
    @RequestMapping("/client")
    @ResponseBody
    public int pushByClient(String device_type, String title, String description, String url) {
        PushMessageBean pushMessageBean = new PushMessageBean(PushMessageBean.CUSTOM_MESSAGE_TYPE.MEDIA.getType(), title, description, url);
        return pushService.pushByClient(device_type, pushMessageBean);
    }

    /**
     * 推送给指定用户
     *
     * @param username
     * @param title
     * @param description
     * @param url
     * @return
     */
    @RequestMapping("/user")
    @ResponseBody
    public boolean pushByUser(String username, String title, String description, String url) {
        PushMessageBean pushMessageBean = new PushMessageBean(PushMessageBean.CUSTOM_MESSAGE_TYPE.MEDIA.getType(), title, description, url);
        return pushService.pushByUser(username, pushMessageBean);
    }

    /**
     * 推送给指定用户
     *
     * @param username
     * @return
     */
    @RequestMapping("/user/reply-notification")
    @ResponseBody
    public boolean pushByUser(String username, String title, String description, String kpi_no, String kpi_name, String statis_date) {
        PushMessageBean pushMessageBean = new PushMessageBean(PushMessageBean.CUSTOM_MESSAGE_TYPE.NOTIFICATION.getType(), title, description);
        pushMessageBean.addParam("tag", "reply");
        pushMessageBean.addParam("kpi_no", kpi_no);
        pushMessageBean.addParam("kpi_name", kpi_name);
        pushMessageBean.addParam("statis_date", statis_date);
        return pushService.pushByUser(username, pushMessageBean);
    }


}

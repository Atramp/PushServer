package com.teradata.pushserver.controller;

import com.teradata.common.bean.http.Response;
import com.teradata.pushserver.bean.message.BDPushMessage;
import com.teradata.pushserver.bean.message.BDPushNotification;
import com.teradata.pushserver.bean.pushbind.BDPushBind;
import com.teradata.pushserver.service.interfaces.PushService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alex on 15-3-27.
 * <p/>
 * mobileBI推送接口
 */

@RestController
@RequestMapping("/push")
public class PushController {
    public static ThreadLocal<String> appID = new ThreadLocal();
    @Autowired
    private PushService pushService;

    /**
     * @param target         android-所有安卓用户 用户编码-指定用户下的所有绑定
     * @param appid          app编码
     * @param custom_content 自定义参数
     */
    @RequestMapping("/message")
    @ResponseBody
    public Response sendMessage(String target, String appid, String custom_content) {
        if (!StringUtils.isEmpty(target) && !StringUtils.isEmpty(appid)) {
            this.appID.set(appid);
            BDPushMessage message = new BDPushMessage();
            message.setCustomContent(custom_content);
            switch (target) {
                case "android": {
                    if (pushService.pushAll(BDPushBind.DEVICE_TYPE.ANDROID.toString(), message))
                        return new Response(Response.RESULT.SUCCESS, "推送成功");
                    break;
                }
                default: {
                    if (pushService.pushByUser(target, message))
                        return new Response(Response.RESULT.SUCCESS, "推送成功");
                    break;
                }
            }
        }
        return new Response(Response.RESULT.FAIL, "推送失败");
    }

    /**
     * @param target         all-全部 android-所有安卓用户 ios-所有苹果用户 用户编码-指定用户下的所有绑定
     * @param appid          app编码
     * @param title          标题
     * @param description    通知内容
     * @param open_type      点击打开的行为（Android有效） 1-打开url 2-自定义行为 3-打开应用（默认）
     * @param url            点击打开的链接
     * @param custom_content 自定义参数
     * @return
     */
    @RequestMapping("/notification")
    @ResponseBody
    public Response sendNotification(String target, String appid, String title, String description, String open_type, String url, String custom_content) {
        if (!StringUtils.isEmpty(target) && !StringUtils.isEmpty(appid)) {
            this.appID.set(appid);
            BDPushNotification notification = new BDPushNotification();
            notification.setTitle(title);
            notification.setDescription(description);
            notification.setOpenType(open_type);
            notification.setUrl(url);
            notification.setCustomContent(custom_content);
            switch (target) {
                case "all": {
                    if (pushService.pushAll(null, notification))
                        return new Response(Response.RESULT.SUCCESS, "推送成功");
                    break;
                }
                case "android": {
                    if (pushService.pushAll(BDPushBind.DEVICE_TYPE.ANDROID.toString(), notification))
                        return new Response(Response.RESULT.SUCCESS, "推送成功");
                    break;
                }
                case "ios": {
                    if (pushService.pushAll(BDPushBind.DEVICE_TYPE.IOS.toString(), notification))
                        return new Response(Response.RESULT.SUCCESS, "推送成功");
                    break;
                }
                default: {
                    if (pushService.pushByUser(target, notification))
                        return new Response(Response.RESULT.SUCCESS, "推送成功");
                    break;
                }
            }
        }
        return new Response(Response.RESULT.FAIL, "推送失败");
    }
}

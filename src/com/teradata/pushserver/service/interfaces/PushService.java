package com.teradata.pushserver.service.interfaces;

import com.teradata.pushserver.bean.message.PushMessageBean;

/**
 * Created by alex on 15-3-31.
 */
public interface PushService {
    /**
     * 向所有用户推送消息
     *
     * @param pushMessageBean
     * @return
     */
    public boolean pushAll(PushMessageBean pushMessageBean);

    /**
     * 向指定类型终端推送消息
     *
     * @param device_type
     * @param pushMessageBean
     * @return
     */
    public int pushByClient(String device_type, PushMessageBean pushMessageBean);

    /**
     * 向指定用户推送消息
     *
     * @param username
     * @param pushMessageBean
     * @return
     */
    public boolean pushByUser(String username, PushMessageBean pushMessageBean);
}

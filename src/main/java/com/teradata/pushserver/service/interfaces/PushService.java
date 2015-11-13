package com.teradata.pushserver.service.interfaces;

import com.teradata.pushserver.bean.message.PushMessage;

/**
 * Created by alex on 15-3-31.
 */
public interface PushService<T extends PushMessage> {
    /**
     * 向所有用户推送消息
     *
     * @param deviceType
     * @param message
     * @return
     */
    boolean pushAll(String deviceType, T message);

    /**
     * 向指定用户推送消息
     *
     * @param username
     * @param message
     * @return
     */
    boolean pushByUser(String username, T message);

}

package com.teradata.pushserver.bean.message;


/**
 * Created by alex on 14-11-19.
 */

/**
 *
 */
public abstract class PushMessage {

    public abstract int getMessageType();

    public abstract void setCustomContent(String customContent);

    public abstract String generate();

}

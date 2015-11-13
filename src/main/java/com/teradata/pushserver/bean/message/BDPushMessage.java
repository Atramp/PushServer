package com.teradata.pushserver.bean.message;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 15/11/3.
 */
public class BDPushMessage extends PushMessage {
    public enum MESSAGE_TYPE {
        MESSAGE(0), NOTIFICATION(1);
        private int value;

        MESSAGE_TYPE(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    protected Map data;

    public BDPushMessage() {
        data = new HashMap();
    }

    @Override
    public int getMessageType() {
        return MESSAGE_TYPE.MESSAGE.value();
    }

    @Override
    public void setCustomContent(String customContent) {
        if (customContent == null || customContent.isEmpty())
            return;
        Map map = new Gson().fromJson(customContent, Map.class);
        data.putAll(map);
    }

    @Override
    public String generate() {
        return new Gson().toJson(data);
    }

}

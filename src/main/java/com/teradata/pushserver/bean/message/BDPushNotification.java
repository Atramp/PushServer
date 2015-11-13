package com.teradata.pushserver.bean.message;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 15/11/3.
 */
public class BDPushNotification extends BDPushMessage {
    private Map aps;

    public BDPushNotification() {
        this.data = new HashMap();
        this.aps = new HashMap();
    }

    public void setTitle(String title) {
        data.put("title", title);
        aps.put("title", title);
        data.put("aps", aps);
    }

    public void setDescription(String description) {
        data.put("description", description);
    }

    public void setOpenType(String openType) {
        data.put("open_type", StringUtils.defaultString(openType, "3"));
    }

    public void setUrl(String url) {
        data.put("url", url);
    }

    @Override
    public int getMessageType() {
        return MESSAGE_TYPE.NOTIFICATION.value();
    }

    @Override
    public void setCustomContent(String customContent) {
        if (customContent == null || customContent.isEmpty())
            return;
        Map map = new Gson().fromJson(customContent, Map.class);
        data.putAll(map);
        data.put("custom_content", customContent);
    }

    @Override
    public String generate() {
        return super.generate();
    }
}

package com.teradata.pushserver.bean.message;


import com.fasterxml.jackson.annotation.JsonRawValue;
import com.google.gson.Gson;
import com.teradata.pushserver.util.DateUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 14-11-19.
 */

/**
 *
 */
public class PushMessageBean {
    // 自定义的消息类型
    public enum CUSTOM_MESSAGE_TYPE {
        MESSAGE("1"), NOTIFICATION("2"), MEDIA("3");
        private String type;

        private CUSTOM_MESSAGE_TYPE(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        @Override
        public String toString() {
            return this.type;
        }
    }

    private String id;
    private String message_type;
    private String message_title;
    private String description;
    private String url;
    private String create_time;
    @JsonRawValue
    private String customer_content;

    private static final String _MSGID = "msgId";//消息ID
    private static final String _TYPE = "message_type";// 消息类型：1-消息，2-通知，3-富媒体
    private static final String _TITLE = "message_title";// 标题
    private static final String _DESCRIPTION = "description";// 消息内容
    private static final String _URL = "url";// 富媒体url

    private Map _custom_content = new HashMap();

    public PushMessageBean() {

    }

    public PushMessageBean(String message_type) {
        this.message_type = message_type;
        this.id = DateUtil.getStandardTimestamp(10);
    }

    public PushMessageBean(String message_type, String message_title, String description) {
        this(message_type);
        this.message_title = message_title;
        this.description = description;
        this.url = "";
    }

    public PushMessageBean(String message_type, String message_title, String description, String url) {
        this(message_type);
        this.message_title = message_title;
        this.description = description;
        this.url = url;
    }

    public String id() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getMessage_title() {
        return message_title;
    }

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCustomer_content() {
        return customer_content;
    }

    public void setCustomer_content(String customer_content) {
        this.customer_content = customer_content;
    }

    public void addParam(String key, String value) {
        _custom_content.put(key, value);
    }

    public String generate() {
        _custom_content.put(_MSGID, this.id);
        _custom_content.put(_TYPE, this.message_type);
        _custom_content.put(_TITLE, this.message_title);
        _custom_content.put(_DESCRIPTION, this.description);
        _custom_content.put(_URL, this.url);
        return customer_content = new Gson().toJson(_custom_content);
    }

}

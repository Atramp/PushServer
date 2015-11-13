package com.teradata.pushserver.bean.pushlog;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alex on 15/10/26.
 * 推送日志Bean
 */
public class PushLog {
    public static final String TARGET_ALL = "All";

    public enum RESULT {
        SUCCESS("success"), FAIL("fail"), ERROR("error");
        private String result;

        RESULT(String result) {
            this.result = result;
        }

        public String getResult() {
            return this.result;
        }
    }

    private String id;
    private String appID;
    private int type;
    private String target;
    private String request;
    private String msgID;
    private String result;
    private String error;
    private String date;

    public PushLog(String appID, int type, String target, String request) {
        this.appID = appID;
        this.type = type;
        this.request = request;
        this.target = target;
        this.date = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(RESULT result) {
        this.result = result.getResult();
    }

    public String getError() {
        return StringUtils.defaultString(error, "");
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}

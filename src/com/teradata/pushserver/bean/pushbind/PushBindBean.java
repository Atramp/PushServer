package com.teradata.pushserver.bean.pushbind;

/**
 * Created by alex on 14-12-4.
 */
public class PushBindBean {
    private String username;
    private String device_type;
    private String bind_time;
    private String rsrv_str1;
    private String rsrv_str2;
    private String rsrv_str3;

    public PushBindBean() {
    }

    public PushBindBean(String username, String device_type) {
        this.username = username;
        this.device_type = device_type;
    }

    public PushBindBean(String username, String device_type, String bind_time) {
        this.username = username;
        this.device_type = device_type;
        this.bind_time = bind_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBind_time() {
        return bind_time;
    }

    public void setBind_time(String bind_time) {
        this.bind_time = bind_time;
    }

    public String getRsrv_str1() {
        return rsrv_str1;
    }

    public void setRsrv_str1(String rsrv_str1) {
        this.rsrv_str1 = rsrv_str1;
    }

    public String getRsrv_str2() {
        return rsrv_str2;
    }

    public void setRsrv_str2(String rsrv_str2) {
        this.rsrv_str2 = rsrv_str2;
    }

    public String getRsrv_str3() {
        return rsrv_str3;
    }

    public void setRsrv_str3(String rsrv_str3) {
        this.rsrv_str3 = rsrv_str3;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }
}

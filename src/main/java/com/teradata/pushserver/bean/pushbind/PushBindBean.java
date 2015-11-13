package com.teradata.pushserver.bean.pushbind;

/**
 * Created by alex on 14-12-4.
 */
public class PushBindBean {
    private String username;
    private String remoteUserID;
    private int deviceType;
    private String bindTime;
    private String rsrvStr1;
    private String rsrvStr2;
    private String rsrvStr3;

    public PushBindBean() {
    }

    public PushBindBean(String username, int deviceType) {
        this.username = username;
        this.deviceType = deviceType;
    }

    public PushBindBean(String username, String bindTime, int deviceType) {
        this.username = username;
        this.deviceType = deviceType;
        this.bindTime = bindTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRemoteUserID() {
        return remoteUserID;
    }

    public void setRemoteUserID(String remoteUserID) {
        this.remoteUserID = remoteUserID;
    }

    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    public String getRsrvStr1() {
        return rsrvStr1;
    }

    public void setRsrvStr1(String rsrvStr1) {
        this.rsrvStr1 = rsrvStr1;
    }

    public String getRsrvStr2() {
        return rsrvStr2;
    }

    public void setRsrvStr2(String rsrvStr2) {
        this.rsrvStr2 = rsrvStr2;
    }

    public String getRsrvStr3() {
        return rsrvStr3;
    }

    public void setRsrvStr3(String rsrvStr3) {
        this.rsrvStr3 = rsrvStr3;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }
}

package com.teradata.pushserver.bean.pushbind;

import java.io.Serializable;

/**
 * Created by alex on 15-3-23.
 */
public class BDPushBindBean extends PushBindBean implements Serializable {
    public enum DEVICE_TYPE {
        ANDROID(3), IOS(4);
        int type;

        DEVICE_TYPE(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public String toString() {
            return String.valueOf(this.type);
        }
    }

    private String baidu_user_id;
    private String baidu_channel_id;


    public BDPushBindBean() {

    }

    public BDPushBindBean(String username, String baidu_user_id, String baidu_channel_id, String device_type) {
        super(username, device_type);
        this.baidu_user_id = baidu_user_id;
        this.baidu_channel_id = baidu_channel_id;
    }

    public BDPushBindBean(String username, String baidu_user_id, String baidu_channel_id, String device_type, String bind_time) {
        super(username, device_type, bind_time);
        this.baidu_user_id = baidu_user_id;
        this.baidu_channel_id = baidu_channel_id;
    }


    public String getBaidu_user_id() {
        return baidu_user_id;
    }

    public void setBaidu_user_id(String baidu_user_id) {
        this.baidu_user_id = baidu_user_id;
    }

    public String getBaidu_channel_id() {
        return baidu_channel_id;
    }

    public void setBaidu_channel_id(String baidu_channel_id) {
        this.baidu_channel_id = baidu_channel_id;
    }
}

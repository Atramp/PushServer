package com.teradata.pushserver.bean.pushbind;

import java.io.Serializable;

/**
 * Created by alex on 15-3-23.
 */
public class BDPushBind extends PushBindBean implements Serializable {
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
}

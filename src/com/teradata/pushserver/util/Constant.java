package com.teradata.pushserver.util;

public class Constant {

    /**
     * 百度消息推送参数
     * 终端类型
     */
    public enum BD_DEVICE_TYPE {
        WEB(1), PC(2), ANDROID(3), IOS(4), WP(5);

        private BD_DEVICE_TYPE(int type) {
            this.type = type;
        }

        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

    }
}

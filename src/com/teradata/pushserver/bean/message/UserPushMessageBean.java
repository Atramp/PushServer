package com.teradata.pushserver.bean.message;


/**
 * Created by alex on 14-11-19.
 */

/**
 *
 */
public class UserPushMessageBean extends PushMessageBean {

    public enum STATUS {
        UNREAD(0), READ(1), DELETED(2);
        private int status;

        private STATUS(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return String.valueOf(this.status);
        }
    }

    private String username;
    private String push_time;
    private String receive_time;
    private String status;


    public UserPushMessageBean() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPush_time() {
        return push_time;
    }

    public void setPush_time(String push_time) {
        this.push_time = push_time;
    }
}

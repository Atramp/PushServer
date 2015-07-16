package com.teradata.pushserver.service.interfaces;

import com.teradata.pushserver.bean.pushbind.PushBindBean;

import java.util.List;

/**
 * Created by alex on 15-3-26.
 */
public interface PushBindService<T extends PushBindBean> {

    public List<T> getAllPushBinds();

    public List<T> getPushBindFromBaidu();

    public List<T> queryPushBindsByUser(String username);

    public boolean bind(T bean);

    public boolean unbind(T bean);

}

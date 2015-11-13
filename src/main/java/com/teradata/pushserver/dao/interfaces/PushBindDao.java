package com.teradata.pushserver.dao.interfaces;


import com.teradata.pushserver.bean.pushbind.PushBindBean;

import java.util.List;

/**
 * Created by alex on 15-3-26.
 */
public interface PushBindDao<T extends PushBindBean> {

    /**
     * 查询所有绑定关系
     *
     * @return
     */
    public List<T> queryPushBinds();

    /**
     * 根据手机号码查询绑定关系
     *
     * @param username
     * @return
     */
    public List<T> queryPushBindsByUser(String username);

    /**
     * 查询一个绑定关系
     *
     * @param bind
     * @return
     */
    public T queryPushBind(T bind);

    /**
     * 插入一条绑定关系
     *
     * @param bind
     * @return
     */
    public int insertPushBind(T bind);


    /**
     * 删除一条绑定关系
     *
     * @param bind
     * @return
     */
    public int deletePushBind(T bind);

    /**
     * 更新一条绑定关系
     *
     * @param bind
     * @return
     */
    public int updatePushBind(T bind);

}

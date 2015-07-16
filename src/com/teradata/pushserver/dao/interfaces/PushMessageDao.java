package com.teradata.pushserver.dao.interfaces;

import com.teradata.pushserver.bean.message.PushMessageBean;
import com.teradata.pushserver.bean.message.UserPushMessageBean;

import java.util.List;

/**
 * Created by alex on 15-3-12.
 */
public interface PushMessageDao {

    /**
     * 查询所有消息列表 (kpi_mbl_bi_push_message)
     *
     * @return
     */
    public List queryAllMessage();

    /**
     * 根据ID查询消息 (kpi_mbl_bi_push_message)
     *
     * @param id
     * @return
     */
    public PushMessageBean queryMessageByID(String id);

    /**
     * 新增一条消息 (kpi_mbl_bi_push_message)
     *
     * @param bean
     * @return
     */
    public int insertMessage(PushMessageBean bean);

    /**
     * 查询用户所有消息 (kpi_mbl_bi_push_user_message)
     *
     * @param username
     * @return
     */
    public List<UserPushMessageBean> queryMessageByUser(String username);

    /**
     * 增加一条用户-信息记录 (kpi_mbl_bi_push_user_message）
     *
     * @param bean
     * @return
     */
    public int insertUserMessage(UserPushMessageBean bean);

    /**
     * 增加一条用户-信息记录 (kpi_mbl_bi_push_user_message）
     *
     * @param bean
     * @return
     */
    public int insertMessageToAllUser(PushMessageBean bean);

    /**
     * 更新一条用户-信息状态 (kpi_mbl_bi_push_user_message）
     *
     * @param username
     * @param messageID
     * @param status
     * @return
     */
    public int updateMessageStatus(String username, String messageID, String status);
}

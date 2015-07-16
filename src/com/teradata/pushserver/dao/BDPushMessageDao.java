package com.teradata.pushserver.dao;

import com.teradata.pushserver.bean.message.PushMessageBean;
import com.teradata.pushserver.bean.message.UserPushMessageBean;
import com.teradata.pushserver.dao.common.AbstractCommonDao;
import com.teradata.pushserver.dao.interfaces.PushMessageDao;
import com.teradata.pushserver.util.DateUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by alex on 15-3-12.
 */
public class BDPushMessageDao extends AbstractCommonDao implements PushMessageDao {

    /**
     * 查询所有消息列表 (kpi_mbl_bi_push_message)
     *
     * @return
     */
    public List queryAllMessage() {
        try (SqlSession session = openSession()) {
            return session.selectList("selectAllMessages", new Param());
        }
    }

    /**
     * 根据ID查询消息 (kpi_mbl_bi_push_message)
     *
     * @param id
     * @return
     */
    public PushMessageBean queryMessageByID(String id) {
        try (SqlSession session = openSession()) {
            Param param = new Param();
            param.put("ID", id);
            return session.selectOne("selectMessageByID", param);
        }
    }

    /**
     * 新增一条消息 (kpi_mbl_bi_push_message)
     *
     * @param bean
     * @return
     */
    public int insertMessage(PushMessageBean bean) {
        try (SqlSession session = openSession()) {
            bean.setCreate_time(DateUtil.getStandardDate());
            Param param = new Param();
            param.put("MESSAGE", bean);
            return session.insert("insertMessage", param);
        }
    }

    /**
     * 查询用户所有消息 (kpi_mbl_bi_push_user_message)
     *
     * @param username
     * @return
     */
    public List<UserPushMessageBean> queryMessageByUser(String username) {
        try (SqlSession session = openSession()) {
            Param param = new Param();
            param.put("USERNAME", username);
            return session.selectList("selectMessagesByUser", param);
        }
    }

    /**
     * 增加一条用户-信息记录 (kpi_mbl_bi_push_user_message）
     *
     * @param bean
     * @return
     */
    public int insertUserMessage(UserPushMessageBean bean) {
        try (SqlSession session = openSession()) {
            bean.setPush_time(DateUtil.getStandardDate());
            Param param = new Param();
            param.put("USER_MESSAGE", bean);
            return session.insert("insertUserMessage", param);
        }
    }

    @Override
    public int insertMessageToAllUser(PushMessageBean bean) {
        try (SqlSession session = openSession()) {
            UserPushMessageBean userPushMessageBean = new UserPushMessageBean();
            userPushMessageBean.setId(bean.getId());
            userPushMessageBean.setStatus(UserPushMessageBean.STATUS.UNREAD.toString());
            userPushMessageBean.setPush_time(DateUtil.getStandardDate());
            Param param = new Param();
            param.put("USER_MESSAGE", userPushMessageBean);
            return session.insert("insertMessageToAllUser", param);
        }
    }

    /**
     * 更新一条用户-信息状态 (kpi_mbl_bi_push_user_message）
     *
     * @param username
     * @param messageID
     * @param status
     * @return
     */
    public int updateMessageStatus(String username, String messageID, String status) {
        try (SqlSession session = openSession()) {
            Param param = new Param();
            param.put("USERNAME", username);
            param.put("MESSAGE_ID", messageID);
            param.put("STATUS", status);
            // 由未读改为已读时，记录接收时间
            if ("1".equals(status))
                param.put("RECEIVE_TIME", DateUtil.getStandardDate());
            return session.update("updateMessageStatus", param);
        }
    }
}

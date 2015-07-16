package com.teradata.pushserver.dao;

import com.teradata.pushserver.bean.pushbind.BDPushBindBean;
import com.teradata.pushserver.dao.common.AbstractCommonDao;
import com.teradata.pushserver.dao.interfaces.PushBindDao;
import com.teradata.pushserver.util.DateUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by alex on 15-3-12.
 */
public class BDPushBindDao extends AbstractCommonDao implements PushBindDao<BDPushBindBean> {

    @Override
    public List<BDPushBindBean> queryPushBinds() {
        try (SqlSession session = openSession()) {
            return session.selectList("selectAllPushBinds", new Param());
        }
    }

    @Override
    public List<BDPushBindBean> queryPushBindsByUser(String username) {
        try (SqlSession session = openSession()) {
            Param param = new Param();
            param.put("USERNAME", username);
            return session.selectList("selectPushBindsByUser", param);
        }
    }

    @Override
    public BDPushBindBean queryPushBind(BDPushBindBean bind) {
        try (SqlSession session = openSession()) {
            Param param = new Param();
            param.put("BIND", bind);
            return session.selectOne("selectPushBind", param);
        }
    }

    @Override
    public int insertPushBind(BDPushBindBean bind) {
        try (SqlSession session = openSession()) {
            bind.setBind_time(DateUtil.getStandardDate());
            Param param = new Param();
            param.put("BIND", bind);
            return session.insert("insertPushBind", param);
        }
    }

    @Override
    public int updatePushBind(BDPushBindBean bind) {
        try (SqlSession session = openSession()) {
            bind.setBind_time(DateUtil.getStandardDate());
            Param param = new Param();
            param.put("BIND", bind);
            return session.update("updatePushBind", param);
        }
    }

    @Override
    public int deletePushBind(BDPushBindBean bind) {
        try (SqlSession session = openSession()) {
            Param param = new Param();
            param.put("BIND", bind);
            return session.delete("deletePushBind", param);
        }
    }
}

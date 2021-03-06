package com.teradata.pushserver.dao;

import com.teradata.pushserver.bean.pushbind.BDPushBind;
import com.teradata.common.dao.AbstractCommonDao;
import com.teradata.pushserver.dao.interfaces.PushBindDao;
import com.teradata.common.util.DateUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by alex on 15-3-12.
 */
public class BDPushBindDao extends AbstractCommonDao implements PushBindDao<BDPushBind> {

    @Override
    public List<BDPushBind> queryPushBinds() {
        try (SqlSession session = openSession()) {
            return session.selectList("selectAllPushBinds", new Param());
        }
    }

    @Override
    public List<BDPushBind> queryPushBindsByUser(String username) {
        try (SqlSession session = openSession()) {
            Param param = new Param();
            param.put("USERNAME", username);
            return session.selectList("selectPushBindsByUser", param);
        }
    }

    @Override
    public BDPushBind queryPushBind(BDPushBind bind) {
        try (SqlSession session = openSession()) {
            Param param = new Param();
            param.put("BIND", bind);
            return session.selectOne("selectPushBind", param);
        }
    }

    @Override
    public int insertPushBind(BDPushBind bind) {
        try (SqlSession session = openSession()) {
            bind.setBindTime(DateUtil.getStandardDate());
            Param param = new Param();
            param.put("BIND", bind);
            return session.insert("insertPushBind", param);
        }
    }

    @Override
    public int updatePushBind(BDPushBind bind) {
        try (SqlSession session = openSession()) {
            bind.setBindTime(DateUtil.getStandardDate());
            Param param = new Param();
            param.put("BIND", bind);
            return session.update("updatePushBind", param);
        }
    }

    @Override
    public int deletePushBind(BDPushBind bind) {
        try (SqlSession session = openSession()) {
            Param param = new Param();
            param.put("BIND", bind);
            return session.delete("deletePushBind", param);
        }
    }
}

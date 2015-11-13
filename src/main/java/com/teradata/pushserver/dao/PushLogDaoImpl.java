package com.teradata.pushserver.dao;

import com.teradata.common.dao.AbstractCommonDao;
import com.teradata.pushserver.bean.pushlog.PushLog;
import com.teradata.pushserver.dao.interfaces.PushLogDao;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by alex on 15/10/28.
 */
public class PushLogDaoImpl extends AbstractCommonDao implements PushLogDao {
    public boolean insertLog(PushLog pushLog) {
        try (SqlSession sqlSession = openSession()) {
            return sqlSession.insert("PUSH_LOG.insertLog", new Param().put("LOG", pushLog)) == 1;
        }
    }
}

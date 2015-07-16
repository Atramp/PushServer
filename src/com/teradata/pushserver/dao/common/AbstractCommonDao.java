package com.teradata.pushserver.dao.common;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * Created by alex on 15-3-26.
 */
public abstract class AbstractCommonDao {
    protected class Param extends HashMap {
        public Param() {
            super();
            put("dbName", "dwttemp");
        }
    }

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    protected SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }

    protected SqlSession openSession(boolean autoCommit) {
        return sqlSessionFactory.openSession(autoCommit);
    }
}

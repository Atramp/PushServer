package com.teradata.common.dao;

import com.teradata.common.Configuration;
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
            put("dbName", Configuration.getString("DATABASE"));
        }

        public Param put(Object key, Object value) {
            super.put(key, value);
            return this;
        }
    }

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    protected SqlSession openSession() {
        return sqlSessionFactory.openSession(true);
    }

    protected SqlSession openSession(boolean autoCommit) {
        return sqlSessionFactory.openSession(autoCommit);
    }
}

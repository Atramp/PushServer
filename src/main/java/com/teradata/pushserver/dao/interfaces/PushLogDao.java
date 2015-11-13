package com.teradata.pushserver.dao.interfaces;

import com.teradata.pushserver.bean.pushlog.PushLog;

/**
 * Created by alex on 15/10/26.
 */
public interface PushLogDao {
    boolean insertLog(PushLog pushLog);
}

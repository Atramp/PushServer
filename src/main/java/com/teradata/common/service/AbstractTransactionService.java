package com.teradata.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

/**
 * Created by alex on 15/10/19.
 */
public abstract class AbstractTransactionService {
    @Autowired
    protected ResourceTransactionManager transactionManager;

    protected TransactionStatus beginTransaction() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        return status;
    }

    protected TransactionStatus beginTransaction(int transactionDefinition) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(transactionDefinition);
        TransactionStatus status = transactionManager.getTransaction(def);
        return status;
    }

    protected void commit(TransactionStatus status) {
        transactionManager.commit(status);
    }

    protected void rollback(TransactionStatus status) {
        transactionManager.rollback(status);
    }

}

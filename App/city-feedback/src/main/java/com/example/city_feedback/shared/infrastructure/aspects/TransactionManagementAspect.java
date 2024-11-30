package com.example.city_feedback.shared.infrastructure.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Component
public class TransactionManagementAspect {

    private static final Logger logger = LoggerFactory.getLogger(TransactionManagementAspect.class);

    private final PlatformTransactionManager transactionManager;

    public TransactionManagementAspect(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * Manages transactions for methods in the application (for all services and repositories).
     * This advice is executed around any method matched by the pointcut expression.
     * It starts a new transaction, proceeds with the method execution, and commits the transaction.
     * If an exception is thrown, it rolls back the transaction.
     *
     * @param joinPoint provides reflective access to the state available at a join point
     * @return the result of the method execution
     * @throws Throwable if the method execution fails
     */
    @Around("@within(org.springframework.stereotype.Service) || @within(org.springframework.stereotype.Repository)")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        TransactionStatus transactionStatus = null;
        try {
            // Start a new transaction
            transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
            logger.info("Transaction started for method: {}", joinPoint.getSignature());

            // Proceed with the target method
            Object result = joinPoint.proceed();

            // Commit the transaction
            transactionManager.commit(transactionStatus);
            logger.info("Transaction committed for method: {}", joinPoint.getSignature());

            return result;
        } catch (Throwable ex) {
            // Rollback the transaction in case of an error
            if (transactionStatus != null && !transactionStatus.isCompleted()) {
                transactionManager.rollback(transactionStatus);
                logger.error("Transaction rolled back for method: {}", joinPoint.getSignature(), ex);
            }
            throw ex;
        }
    }
}

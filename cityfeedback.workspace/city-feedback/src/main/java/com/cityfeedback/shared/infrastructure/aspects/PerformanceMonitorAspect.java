package com.cityfeedback.shared.infrastructure.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMonitorAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitorAspect.class);

    /**
     * Monitors the performance of methods.
     * This advice is executed around any method matched by the pointcut expression.
     * It logs the method signature and the time taken to execute the method.
     *
     * @param joinPoint provides reflective access to the state available at a join point
     * @return the result of the method execution
     * @throws Throwable if the method execution fails
     */
    @Around("execution(* com.example..*(..))")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        logger.info("Method [{}] executed in {} ms", joinPoint.getSignature(), executionTime);

        return result;
    }
}

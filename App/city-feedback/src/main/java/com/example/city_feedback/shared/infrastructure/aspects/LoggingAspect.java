package com.example.city_feedback.shared.infrastructure.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Pointcut to match all methods in the application.
     * This pointcut matches the execution of any method within the `com.example` package and its sub-packages.
     */
    @Pointcut("execution(* com.example..*(..))")
    public void applicationMethods() {}

    /**
     * Logs the entry of a method.
     * This advice is executed before any method matched by the `applicationMethods` pointcut.
     * It logs the method signature and its arguments.
     *
     * @param joinPoint provides reflective access to the state available at a join point
     */
    @Before("applicationMethods()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    /**
     * Logs the exit of a method on successful execution.
     * This advice is executed after any method matched by the `applicationMethods` pointcut returns successfully.
     * It logs the method signature and its result.
     *
     * @param joinPoint provides reflective access to the state available at a join point
     * @param result the result returned by the method
     */
    @AfterReturning(pointcut = "applicationMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method: {} with result: {}", joinPoint.getSignature(), result);
    }

    /**
     * Logs exceptions thrown by methods.
     * This advice is executed after any method matched by the `applicationMethods` pointcut throws an exception.
     * It logs the method signature and the exception message.
     *
     * @param joinPoint provides reflective access to the state available at a join point
     * @param exception the exception thrown by the method
     */
    @AfterThrowing(pointcut = "applicationMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {} with cause: {}", joinPoint.getSignature(), exception.getMessage());
    }
}

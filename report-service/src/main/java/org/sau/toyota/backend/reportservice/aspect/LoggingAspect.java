package org.sau.toyota.backend.reportservice.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/** @author Ahmet Alıç
 * @since 28-06-2024
 * Aspect for logging execution of service methods in the report service.
 */
@Aspect
@Component
@Log4j2
public class LoggingAspect {
    /**
     * Pointcut that matches all methods in classes within the
     * org.sau.toyota.backend.reportservice.service.Concrete package.
     */
    @Pointcut("execution(* org.sau.toyota.backend.reportservice.service.Concrete.*.*(..))")
    public void loggingPointCut() {}
    /**
     * Advice that logs the method execution details before and after the method is invoked.
     *
     * @param joinPoint the join point representing the method being executed
     * @return the result of the method execution
     * @throws Throwable if any error occurs during method execution
     */
    @Around("loggingPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.trace("method invoked "  +": " + methodName);
        Object object = joinPoint.proceed();
        log.trace("method ends ... " + methodName);
        return object;
    }
}
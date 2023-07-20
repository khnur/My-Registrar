package com.example.myregistrar.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AnnotatedAdvice {
    @Pointcut("execution(* com.example.myregistrar.util.CLI.*(..))")
    public void pointCut() {}

    @Before("pointCut()")
    public void logMethodExecution() {
        log.info("Executing method matching the pointcut");
    }

    @Before(value = "pointCut() && args(arg1, arg2)", argNames = "arg1,arg2")
    public void logMethodExecutionWithArguments(String arg1, int arg2) {
        log.info("Executing method matching pointcut with arguments: {}, {}", arg1, arg2);
    }

    @Before("pointCut() && within(com.example.myregistrar.services.*)")
    public void logMethodExecutionWithinServicesPackage() {
        log.info("Executing method matching pointcut within services package");
    }

    @Before("execution(* com.example.myregistrar.util.CLI.*(..))")
    public void logMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Executing method: {}", methodName);
    }

    @AfterReturning(pointcut = "execution(* com.example.myregistrar.util.CLI.*(..))", returning = "result")
    public void logMethodExecution(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method execution completed: {}, Result: {}", methodName, result);
    }

    @AfterThrowing(pointcut = "execution(* com.example.myregistrar.util.CLI.*(..))", throwing = "ex")
    public void logMethodException(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        log.error("Exception occurred in method: {}, Exception: {}", methodName, ex.getMessage());
    }

    @Around("execution(* com.example.myregistrar.util.CLI.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Entering method: {}", methodName);

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            log.error("Exception occurred in method: {}, Exception: {}", methodName, ex.getMessage());
        }

        log.info("Exiting method: {}", methodName);
        return result;
    }
}

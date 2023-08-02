package com.example.myregistrar.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class ControllerAdvice {
    @Pointcut("execution(public * com.example.myregistrar.controllers.*.create*(..))")
    public void createPointCut() {}

    @Pointcut("execution(public com.example.myregistrar.dtos.* com.example.myregistrar.controllers.*.get*(..))")
    public void getDtoPointCut() {}

    @Pointcut("execution(public java.util.List<com.example.myregistrar.dtos.*> com.example.myregistrar.controllers.*.get*(..))")
    public void getListPointCut() {}

    @Pointcut("execution(public com.example.myregistrar.dtos.* com.example.myregistrar.controllers.*.assign*(..))")
    public void assignPointCut() {}

    @Pointcut("execution(public * com.example.myregistrar.controllers.*.*(..))")
    public void allControllerMethodsPointCut() {}

    @Pointcut("@within(com.example.myregistrar.annotation.LogDuration) || @annotation(com.example.myregistrar.annotation.LogDuration)")
    public void logDurationPointCut() {}

    @Before("allControllerMethodsPointCut()")
    public void logBeforeAllControllerMethods(JoinPoint joinPoint) {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        log.info("Request IP Address: {}", httpServletRequest.getRemoteAddr());
        log.info("Request URL: {}", httpServletRequest.getRequestURL().toString());
        log.info("Request User: {}", httpServletRequest.getRemoteUser());
    }

    @AfterThrowing(pointcut = "allControllerMethodsPointCut()", throwing = "ex")
    public void afterThrowingServiceMethods(JoinPoint joinPoint, Exception ex) {
        String declaringMethodType = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.error("After Throwing Exception in [{}] {} method within the service layer", declaringMethodType, methodName);
        log.error("An exception occurred: {}", ex.getMessage());
    }

    @AfterReturning(pointcut = "allControllerMethodsPointCut()", returning = "result")
    public void logAfterReturningAllControllerMethods(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        if (methodSignature == null || methodSignature.getName() == null) {
            log.error("Some shit went wrong");
            return;
        }

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getResponse();

        if (response != null) {
            log.info("Response Status: {}", response.getStatus());
        } else {
            log.error("Some shit went wrong in method [{}] {}(..)", result.getClass(), methodSignature.getName());
        }
    }

    @Around("logDurationPointCut() && within(com.example.myregistrar.controllers..*)")
    public Object logTimeDuration(ProceedingJoinPoint joinPoint) throws Throwable {
        String declaringMethodType = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        long start = System.currentTimeMillis();

        Object res = joinPoint.proceed();

        long end = System.currentTimeMillis();

        log.info("Execution time of [{}] {} method method took {} ms",
                declaringMethodType, methodName, end - start);

        return res;
    }
}

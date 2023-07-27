package com.example.myregistrar.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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

    @Before("allControllerMethodsPointCut()")
    public void logBeforeAllControllerMethods(JoinPoint joinPoint) {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        log.info("Request IP Address: {}", httpServletRequest.getRemoteAddr());
        log.info("Request URL: {}", httpServletRequest.getRequestURL().toString());
        log.info("Request User: {}", httpServletRequest.getRemoteUser());
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
}

package com.example.myregistrar.aop;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceAdvice {
    @Pointcut("execution(* com.example.myregistrar.services.*.get*(..))")
    public void getPointCut() {}

    @Pointcut("execution(* com.example.myregistrar.services.*.assign*(..))")
    public void assignPointCut() {}

    @Pointcut("execution(* com.example.myregistrar.services.*.get*(..)) || execution(* com.example.myregistrar.services.*.assign*(..))")
    public void getAndAssignMethod() {}

    @Before("getPointCut()")
    public void logGetMethodExecutionWithinServicesPackage() {
        log.info("Executing get method matching pointcut within services package");
    }

    @Around("execution(public void com.example.myregistrar.services.*.create*(..))")
    public Object logCreateEntity(ProceedingJoinPoint pjp) {
        String message = "Attempt to create ";
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();

        Object[] arguments = pjp.getArgs();
        if (arguments == null || arguments.length == 0) {
            log.error(message + "an entity. No object provided");
            return null;
        } else if (arguments.length > 1) {
            log.error(message + "an entity. Too many arguments");
            return null;
        } else if (arguments[0] == null) {
            log.error(message + "an entity. The object is null");
            return null;
        }

        boolean[] methodNameChecks = new boolean[] {
                methodSignature.getName().equals("createStudent"),
                methodSignature.getName().equals("createCourse"),
                methodSignature.getName().equals("createBook")
        };

        if (methodNameChecks[0]) {
            log.info(message + "a student");
        } else if (methodNameChecks[1]) {
            log.info(message + "a course");
        } else if (methodNameChecks[2]) {
            log.info(message + "a book");
        } else {
            log.error(message + "an entity. Method Not Allowed");
            return null;
        }

        Object result;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
            return null;
        }

        message = " is added to database with name \"{}\"";
        if (methodNameChecks[0]) {
            log.info("Student" + message, ((Student) arguments[0]).getFirstName());
        } else if (methodNameChecks[1]) {
            log.info("Course" + message, ((Course) arguments[0]).getName());
        } else if (methodNameChecks[2]) {
            log.info("Book" + message, ((Book) arguments[0]).getName());
        } else {
            log.error("Method Not Allowed");
            return null;
        }

        return result;
    }

    @Around("execution(public void com.example.myregistrar.services.*.generate*(..))")
    public Object logGenerateRandomEntities(ProceedingJoinPoint proceedingJoinPoint) {
        String message = "Attempt to create random entities";
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        Object[] arguments = proceedingJoinPoint.getArgs();
        if (arguments == null || arguments.length == 0) {
            log.error(message + ". No object provided");
            return null;
        } else if (arguments.length > 1) {
            log.error(message + ". Too many arguments");
            return null;
        } else if (arguments[0] == null) {
            log.error(message + ". The object is null");
            return null;
        } else if (arguments[0] instanceof Integer && (Integer) arguments[0] <= 0) {
            log.error(message + ". Invalid number of new entities");
            return null;
        }

        boolean[] methodNameChecks = new boolean[] {
                methodSignature.getName().equals("generateRandomStudents"),
                methodSignature.getName().equals("generateRandomCourses"),
                methodSignature.getName().equals("generateRandomBooks")
        };

        if (methodNameChecks[0]) {
            log.info(message + " of Student");
        } else if (methodNameChecks[1]) {
            log.info(message + " of Course");
        } else if (methodNameChecks[2]) {
            log.info(message + " of Book");
        } else {
            log.error(message + " an entity. Method Not Allowed");
            return null;
        }

        Object result;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
            return null;
        }

        message = " are added to database";
        Integer amount = (int) arguments[0];

        if (methodNameChecks[0]) {
            log.info("{} students" + message, amount);
        } else if (methodNameChecks[1]) {
            log.info("{} courses" + message, amount);
        } else if (methodNameChecks[2]) {
            log.info("{} books" + message, amount);
        } else {
            log.error("Method Not Allowed");
            return null;
        }

        return result;
    }

    @AfterThrowing(pointcut = "getAndAssignMethod()", throwing = "ex")
    public void afterThrowingServiceMethods(JoinPoint joinPoint, Exception ex) {
        String declaringMethodType = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.error("After Throwing Exception in [{}] {} method within the service layer", declaringMethodType, methodName);
        log.error("Exception: {}", ex.getMessage());
    }

    @AfterReturning(pointcut = "getPointCut()", returning = "result")
    public void afterReturningGetMethods(JoinPoint joinPoint, Object result) {
        String declaringMethodType = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info("Date retrieved from database. Method [{}] {} completed. Result type: {}",
                declaringMethodType, methodName, result.getClass().getSimpleName());
    }

    @AfterReturning(pointcut = "assignPointCut()")
    public void afterReturningAssignMethods(JoinPoint joinPoint) {
        String declaringMethodType = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info("Date dependency changed in database. Method [{}] {} completed.",
                declaringMethodType, methodName);
    }
}

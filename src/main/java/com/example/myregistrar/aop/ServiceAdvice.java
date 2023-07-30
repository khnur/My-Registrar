package com.example.myregistrar.aop;

import com.example.myregistrar.models.*;
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
    @Pointcut("execution(public com.example.myregistrar.models.* com.example.myregistrar.services.*.get*(..))")
    public void getModelPointCut() {}

    @Pointcut("execution(public java.util.List<com.example.myregistrar.models.*> com.example.myregistrar.services.*.get*(..))")
    public void getListPointCut() {}

    @Pointcut("execution(* com.example.myregistrar.services.*.get*(..))")
    public void getPointCut() {}

    @Pointcut("execution(* com.example.myregistrar.services.*.assign*(..))")
    public void assignPointCut() {}

    @Pointcut("execution(* com.example.myregistrar.services.*.*(..))")
    public void getAllServiceMethods() {}

    @Pointcut("execution(public * com.example.myregistrar.services.*.create*(..))")
    public void createMethodPointCut() {}

    @Before("getPointCut()")
    public void logGetMethodExecutionWithinServicesPackage() {
        log.info("Executing get method matching pointcut within services package");
    }

    @Before("assignPointCut()")
    public void logAssignMethodExecutionWithinServicesPackage() {
        log.info("Executing assign method matching pointcut within services package");
    }

    @Before("createMethodPointCut()")
    public void logCreateMethodExecution(JoinPoint joinPoint) {
        String message = "Attempt to create ";
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Object[] arguments = joinPoint.getArgs();
        if (arguments == null || arguments.length == 0) {
            log.error(message + "an entity. No object provided");
            return;
        } else if (arguments.length > 1) {
            log.error(message + "an entity. Too many arguments");
            return;
        } else if (arguments[0] == null) {
            log.error(message + "an entity. The object is null");
            return;
        }

        if (methodSignature == null || methodSignature.getName() == null) {
            log.error("Some shit went wrong");
            return;
        }

        if (methodSignature.getName().equals("createStudent")) {
            log.info(message + "a student");
        } else if (methodSignature.getName().equals("createCourse")) {
            log.info(message + "a course");
        } else if (methodSignature.getName().equals("createBook")) {
            log.info(message + "a book");
        } else if (methodSignature.getName().equals("createUniversity")) {
            log.info(message + " an university");
        } else if (methodSignature.getName().equals("createUser")) {
            log.info(message + " an user");
        } else {
            log.error(message + "an entity. Method Not Allowed");
        }
    }

    @AfterReturning(pointcut = "createMethodPointCut()", returning = "result")
    public void logCreateEntity(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        if (methodSignature == null || methodSignature.getName() == null) {
            log.error("Some shit went wrong");
            return;
        }

        String message = " is added to database with name \"{}\"";
        if (methodSignature.getName().equals("createStudent")) {
            log.info("Student" + message, ((Student) result).getFirstName());
        } else if (methodSignature.getName().equals("createCourse")) {
            log.info("Course" + message, ((Course) result).getName());
        } else if (methodSignature.getName().equals("createBook")) {
            log.info("Book" + message, ((Book) result).getName());
        } else if (methodSignature.getName().equals("createUniversity")) {
            log.info("University" + message, ((University) result).getName());
        } else if (methodSignature.getName().equals("createUser")) {
            log.info("User" + message, ((EndUser) result).getUsername());
        }  else {
            log.error("Method Not Allowed");
        }
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
                methodSignature.getName().equals("generateRandomBooks")};

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

    @AfterThrowing(pointcut = "getAllServiceMethods()", throwing = "ex")
    public void afterThrowingServiceMethods(JoinPoint joinPoint, Exception ex) {
        String declaringMethodType = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.error("After Throwing Exception in [{}] {} method within the service layer", declaringMethodType, methodName);
        log.error("An exception occurred: {}", ex.getMessage());
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

    @Before("execution(public * com.example.myregistrar.services.*.notify*(..))")
    public void logNotifyStudents() {
        log.info("Notifying students about new course with the university");
    }

    @AfterReturning(pointcut = "execution(public * com.example.myregistrar.services.*.notify*(..))")
    public void afterReturningNotifyMethods() {
        log.info("All students with the university have been notified about new course");
    }


//    @Around("getListPointCut()")
//    public Object handleListNotFoundException(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            return joinPoint.proceed();
//        } catch (RuntimeException ex) {
//            return Collections.emptyList();
//        }
//    }

//    @Around("getModelPointCut()")
//    public Object handleGetMethods(ProceedingJoinPoint joinPoint) throws Throwable {
//        Class<?> clazz = ((MethodSignature) joinPoint.getSignature()).getReturnType();
//        try {
//            return joinPoint.proceed();
//        } catch (RuntimeException ex) {
//            return clazz.getDeclaredConstructor().newInstance();
//        }
//    }
}

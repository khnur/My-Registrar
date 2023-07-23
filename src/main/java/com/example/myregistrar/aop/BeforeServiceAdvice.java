package com.example.myregistrar.aop;

import com.example.myregistrar.models.Book;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.Student;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class BeforeServiceAdvice {
    @Around("execution(public void com.example.myregistrar.services.*.create*(..))")
    public Object logCreate(ProceedingJoinPoint pjp) {
        String message = "Attempt to create ";
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();

        Object[] arguments = pjp.getArgs();
        if (arguments == null || arguments.length == 0) {
            log.error(message + "an entity. No object provided");
            return null;
        } else if (arguments.length > 1) {
            log.error(message + "an entity. Method Not Allowed");
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

}

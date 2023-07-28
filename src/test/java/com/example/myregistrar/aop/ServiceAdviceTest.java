package com.example.myregistrar.aop;

import com.example.myregistrar.models.Course;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

class ServiceAdviceTest {
    @Mock
    Logger log;
    @InjectMocks
    ServiceAdvice serviceAdvice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetModelPointCut() {
        serviceAdvice.getModelPointCut();
    }

    @Test
    void testGetListPointCut() {
        serviceAdvice.getListPointCut();
    }

    @Test
    void testGetPointCut() {
        serviceAdvice.getPointCut();
    }

    @Test
    void testAssignPointCut() {
        serviceAdvice.assignPointCut();
    }

    @Test
    void testGetAllServiceMethods() {
        serviceAdvice.getAllServiceMethods();
    }

    @Test
    void testCreateMethodPointCut() {
        serviceAdvice.createMethodPointCut();
    }

}
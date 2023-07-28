package com.example.myregistrar.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

class ControllerAdviceTest {
    @Mock
    Logger log;
    @InjectMocks
    ControllerAdvice controllerAdvice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePointCut() {
        controllerAdvice.createPointCut();
    }

    @Test
    void testGetDtoPointCut() {
        controllerAdvice.getDtoPointCut();
    }

    @Test
    void testGetListPointCut() {
        controllerAdvice.getListPointCut();
    }

    @Test
    void testAssignPointCut() {
        controllerAdvice.assignPointCut();
    }

    @Test
    void testAllControllerMethodsPointCut() {
        controllerAdvice.allControllerMethodsPointCut();
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
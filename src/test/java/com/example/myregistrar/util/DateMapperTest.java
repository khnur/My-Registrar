package com.example.myregistrar.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.*;

class DateMapperTest {
    @Mock
    SimpleDateFormat DATE_FORMAT;
    @Mock
    Calendar CALENDAR_TODAY;
    @InjectMocks
    DateMapper dateMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGET_AGE() {
        int result = DateMapper.GET_AGE(new GregorianCalendar(2023, Calendar.JULY, 28, 18, 45).getTime());
        Assertions.assertEquals(0, result);
    }

    @Test
    void testGET_AGE_More() {
        when(CALENDAR_TODAY.get(Calendar.YEAR)).thenReturn(2023);
        when(CALENDAR_TODAY.get(Calendar.MONTH)).thenReturn(Calendar.JULY);
        when(CALENDAR_TODAY.get(Calendar.DAY_OF_MONTH)).thenReturn(28);

        Date dob = new GregorianCalendar(2000, Calendar.JULY, 28, 18, 45).getTime();
        int expectedResult = 23;

        int result = DateMapper.GET_AGE(dob);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testGET_AGEBeforeBirthday() {
        when(CALENDAR_TODAY.get(Calendar.YEAR)).thenReturn(2023);
        when(CALENDAR_TODAY.get(Calendar.MONTH)).thenReturn(Calendar.JULY);
        when(CALENDAR_TODAY.get(Calendar.DAY_OF_MONTH)).thenReturn(26);

        Date dob = new GregorianCalendar(2000, Calendar.JULY, 28, 18, 45).getTime();
        int expectedResult = 23;

        int result = DateMapper.GET_AGE(dob);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testGET_AGEOnBirthday() {
        when(CALENDAR_TODAY.get(Calendar.YEAR)).thenReturn(2023);
        when(CALENDAR_TODAY.get(Calendar.MONTH)).thenReturn(Calendar.JULY);
        when(CALENDAR_TODAY.get(Calendar.DAY_OF_MONTH)).thenReturn(28);

        Date dob = new GregorianCalendar(2000, Calendar.JULY, 28, 18, 45).getTime();
        int expectedResult = 23;

        int result = DateMapper.GET_AGE(dob);

        Assertions.assertEquals(expectedResult, result);
    }
    @Test
    void testGET_AGEAfterBirthday() {
        when(CALENDAR_TODAY.get(Calendar.YEAR)).thenReturn(2023);
        when(CALENDAR_TODAY.get(Calendar.MONTH)).thenReturn(Calendar.JULY);
        when(CALENDAR_TODAY.get(Calendar.DAY_OF_MONTH)).thenReturn(30);

        Date dob = new GregorianCalendar(2000, Calendar.JULY, 28, 18, 45).getTime();
        int expectedResult = 23;

        int result = DateMapper.GET_AGE(dob);

        Assertions.assertEquals(expectedResult, result);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
package com.example.myregistrar.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.*;

public class DateMapperTest {
    @Mock
    SimpleDateFormat DATE_FORMAT;
    @Mock
    Calendar CALENDAR_TODAY;
    @InjectMocks
    DateMapper dateMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGET_AGE() throws Exception {
        int result = DateMapper.GET_AGE(new GregorianCalendar(2023, Calendar.JULY, 21, 3, 0).getTime());
        Assert.assertEquals(0, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
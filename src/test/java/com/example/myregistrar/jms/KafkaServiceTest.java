package com.example.myregistrar.jms;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.util.JsonMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

public class KafkaServiceTest {
    @Mock
    KafkaTemplate<String, String> kafkaTemplate;
    @Mock
    StudentService studentService;
    @Mock
    Logger log;
    @InjectMocks
    KafkaService kafkaService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendToCourseTopic() {
        kafkaService.sendToCourseTopic("object");

        verify(kafkaTemplate).send(eq(KafkaConfig.COURSE_CREATION_TOPIC), eq("object"));
    }

    @Test
    public void testListenMessage() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of(new Student("firstName", "lastName", new GregorianCalendar(2023, Calendar.JULY, 24, 18, 58).getTime(), "gender")));

        kafkaService.listenMessage("courseDtoJson");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
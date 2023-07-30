package com.example.myregistrar.controllers;

import com.example.myregistrar.controllers.facade.BookFacade;
import com.example.myregistrar.controllers.facade.CourseFacade;
import com.example.myregistrar.controllers.facade.StudentFacade;
import com.example.myregistrar.controllers.facade.UniversityFacade;
import com.example.myregistrar.dtos.auth_dto.ErrorDto;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    StudentFacade studentFacade;

    @Test
    void testGetStudentById() {
        when(studentFacade.getStudentById(anyLong())).thenReturn(StudentMapper.INSTANCE.studentToStudentDto(new Student()));
        ResponseEntity<ErrorDto> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/1", ErrorDto.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
    }
}

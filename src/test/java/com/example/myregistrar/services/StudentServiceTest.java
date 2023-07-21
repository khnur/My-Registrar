package com.example.myregistrar.services;

import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.StudentRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class StudentServiceTest {
    @Mock
    StudentRepo studentRepo;
    @InjectMocks
    StudentService studentService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateStudent() throws Exception {
        when(studentRepo.existsStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(true);

        studentService.createStudent(new Student("firstName", "lastName", null, "gender"));
    }

    @Test
    public void testCreateStudent2() throws Exception {
        when(studentRepo.existsStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(true);

        studentService.createStudent(new StudentDto(null, null, null, "gender"));
    }

    @Test
    public void testCreateRandomStudents() throws Exception {
        when(studentRepo.existsStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(true);

        studentService.createRandomStudents(0);
    }

    @Test
    public void testGetAllStudents() throws Exception {
        when(studentRepo.findAll()).thenReturn(List.of(new Student(null, null, null, "gender")));

        List<Student> result = studentService.getAllStudents();
        Assert.assertEquals(List.of(new Student(null, null, null, "gender")), result);
    }

    @Test
    public void testGetStudentsByFirstName() throws Exception {
        when(studentRepo.findStudentsByFirstName(anyString())).thenReturn(List.of(new Student(null, null, null, "gender")));

        List<Student> result = studentService.getStudentsByFirstName("firstName");
        Assert.assertEquals(List.of(new Student(null, null, null, "gender")), result);
    }

    @Test
    public void testGetStudentsByLastName() throws Exception {
        when(studentRepo.findStudentsByLastName(anyString())).thenReturn(List.of(new Student(null, null, null, "gender")));

        List<Student> result = studentService.getStudentsByLastName("lastName");
        Assert.assertEquals(List.of(new Student(null, null, null, "gender")), result);
    }

    @Test
    public void testGetStudentByFirstNameAndLastName() throws Exception {
        when(studentRepo.findStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(null);

        Student result = studentService.getStudentByFirstNameAndLastName("firstName", "lastName");
        Assert.assertEquals(new Student(null, null, null, "gender"), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
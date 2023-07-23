package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.exceptions.StudentAlreadyExistsException;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.StudentRepo;
import com.example.myregistrar.util.DateMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class StudentServiceImplTest {
    @Mock
    StudentRepo studentRepo;
    @Mock
    Logger log;
    @InjectMocks
    StudentServiceImpl studentServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateStudent() throws Exception {
        when(studentRepo.existsStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(true);

        studentServiceImpl.createStudent(new Student("firstName", "lastName", DateMapper.DATE_FORMAT.parse("1247-74-77"), "m"));
    }

    @Test(expected = StudentAlreadyExistsException.class)
    public void testCreateStudent_StudentAlreadyExistsException() throws Exception {
        when(studentRepo.existsStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(true);

        studentServiceImpl.createStudent(new Student("firstName", "lastName", DateMapper.DATE_FORMAT.parse("1247-74-77"), "m"));
    }

    @Test
    public void testCreateRandomStudents() throws Exception {
        when(studentRepo.existsStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(true);

        studentServiceImpl.generateRandomStudents(0);
    }

    @Test
    public void testGetAllStudents() throws Exception {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("firstName1", "lastName1", DateMapper.DATE_FORMAT.parse("1247-74-77"), "m"));
        studentList.add(new Student("firstName2", "lastName2", DateMapper.DATE_FORMAT.parse("1247-74-77"), "m"));
        when(studentRepo.findAll()).thenReturn(studentList);

        List<Student> result = studentServiceImpl.getAllStudents();
        Assert.assertEquals(studentList, result);
    }

    @Test
    public void testGetStudentsByFirstName() throws Exception {
        when(studentRepo.findStudentsByFirstName(anyString())).thenReturn(List.of(new Student("firstName", "lastName", DateMapper.DATE_FORMAT.parse("1247-74-77"), "gender")));

        List<Student> result = studentServiceImpl.getStudentsByFirstName("firstName");
        Assert.assertEquals(List.of(new Student("firstName", "lastName", DateMapper.DATE_FORMAT.parse("1247-74-77"), "gender")), result);
    }

    @Test
    public void testGetStudentsByLastName() throws Exception {
        when(studentRepo.findStudentsByLastName(anyString())).thenReturn(List.of(new Student("firstName", "lastName", DateMapper.DATE_FORMAT.parse("1247-74-77"), "gender")));

        List<Student> result = studentServiceImpl.getStudentsByLastName("lastName");
        Assert.assertEquals(List.of(new Student("firstName", "lastName", DateMapper.DATE_FORMAT.parse("1247-74-77"), "gender")), result);
    }

    @Test
    public void testGetStudentByFirstNameAndLastName() throws Exception {
        when(studentRepo.findStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(null);

        Student result = studentServiceImpl.getStudentByFirstNameAndLastName("firstName", "lastName");
        Assert.assertEquals(new Student("firstName", "lastName", DateMapper.DATE_FORMAT.parse("1247-74-77"), "gender"), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
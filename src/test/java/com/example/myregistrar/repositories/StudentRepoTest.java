package com.example.myregistrar.repositories;

import com.example.myregistrar.models.Student;
import jakarta.persistence.EntityManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class StudentRepoTest {
    @Mock
    EntityManager entityManager;
    @InjectMocks
    StudentRepo studentRepo;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExistsStudentByFirstNameAndLastName() throws Exception {
        boolean result = studentRepo.existsStudentByFirstNameAndLastName("firstName", "lastName");
        Assert.assertEquals(true, result);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Student> result = studentRepo.findAll();
        Assert.assertEquals(List.of(new Student(null, null, null, "gender")), result);
    }

    @Test
    public void testFindStudentsByFirstName() throws Exception {
        List<Student> result = studentRepo.findStudentsByFirstName("firstName");
        Assert.assertEquals(List.of(new Student(null, null, null, "gender")), result);
    }

    @Test
    public void testFindStudentsByLastName() throws Exception {
        List<Student> result = studentRepo.findStudentsByLastName("lastName");
        Assert.assertEquals(List.of(new Student(null, null, null, "gender")), result);
    }

    @Test
    public void testFindStudentByFirstNameAndLastName() throws Exception {
        Optional<Student> result = studentRepo.findStudentByFirstNameAndLastName("firstName", "lastName");
        Assert.assertEquals(null, result);
    }

    @Test
    public void testSave() throws Exception {
        studentRepo.save(new Student(null, null, null, "gender"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
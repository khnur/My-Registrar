package com.example.myregistrar.dtos;

import com.example.myregistrar.util.DateMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

public class StudentDtoTest {
    @Mock
    Date birthDate;
    @Mock
    List<CourseDto> courseDtoList;
    @InjectMocks
    StudentDto studentDto;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetters() {
        StudentDto studentDto = new StudentDto();

        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        Date birthDate = new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime();
        String gender = "Male";
        String email = "john.doe@onelab.kz";

        studentDto.setId(id);
        studentDto.setFirstName(firstName);
        studentDto.setLastName(lastName);
        studentDto.setBirthDate(birthDate);
        studentDto.setGender(gender);
        studentDto.setEmail(email);

        Assert.assertEquals(id, studentDto.getId());
        Assert.assertEquals(firstName, studentDto.getFirstName());
        Assert.assertEquals(lastName, studentDto.getLastName());
        Assert.assertEquals(birthDate, studentDto.getBirthDate());
        Assert.assertEquals(gender, studentDto.getGender());
        Assert.assertEquals(email, studentDto.getEmail());
    }


    @Test
    public void testSetId() throws Exception {
        studentDto.setId(Long.valueOf(1));
    }

    @Test
    public void testSetFirstName() throws Exception {
        studentDto.setFirstName("firstName");
    }

    @Test
    public void testSetLastName() throws Exception {
        studentDto.setLastName("lastName");
    }

    @Test
    public void testSetBirthDate() throws Exception {
        studentDto.setBirthDate(new GregorianCalendar(2023, Calendar.JULY, 21, 17, 8).getTime());
    }

    @Test
    public void testSetAge() throws Exception {
        studentDto.setAge(Integer.valueOf(0));
    }

    @Test
    public void testSetGender() throws Exception {
        studentDto.setGender("gender");
    }

    @Test
    public void testSetEmail() throws Exception {
        studentDto.setEmail("email");
    }

    @Test
    public void testSetCourseDtoList() throws Exception {
        studentDto.setCourseDtoList(List.of(new CourseDto("name", "university", "department", "instructor", Integer.valueOf(0))));
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = studentDto.equals("o");
        Assert.assertEquals(true, result);
    }

    @Test
    public void testCanEqual() {
        StudentDto studentDto1 = new StudentDto("John", "Doe", new Date(), "Male");
        StudentDto studentDto2 = new StudentDto("John", "Doe", new Date(), "Male");
        StudentDto studentDto3 = new StudentDto("Jane", "Smith", new Date(), "Female");

        Assert.assertTrue(studentDto1.canEqual(studentDto2));
        Assert.assertTrue(studentDto2.canEqual(studentDto1));
        Assert.assertTrue(studentDto1.canEqual(studentDto1));
        Assert.assertFalse(studentDto1.canEqual(studentDto3));
    }

    @Test
    public void testHashCode() throws Exception {
        int result = studentDto.hashCode();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testToString() {
        String firstName = "John";
        String lastName = "Doe";
        Date birthDate = new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime();
        String gender = "Male";
        String email = "john.doe@onelab.kz";

        studentDto.setFirstName(firstName);
        studentDto.setLastName(lastName);
        studentDto.setBirthDate(birthDate);
        studentDto.setGender(gender);
        studentDto.setEmail(email);

        String expectedToStringResult = "StudentDto(id=null, firstName=John, lastName=Doe, " +
                "birthDate=" + DateMapper.DATE_FORMAT.format(birthDate) + ", age=23, gender=Male, email=john.doe@onelab.kz)";

        Assert.assertEquals(expectedToStringResult, studentDto.toString());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
package com.example.myregistrar.models;

import com.example.myregistrar.embeddables.CoursePreRequisiteId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CoursePreRequisiteTest {
    @Mock
    CoursePreRequisiteId id;
    @Mock
    Course course;
    @Mock
    Course coursePreReq;
    @InjectMocks
    CoursePreRequisite coursePreRequisite;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSetId() throws Exception {
        coursePreRequisite.setId(new CoursePreRequisiteId(Long.valueOf(1), Long.valueOf(1)));
    }

    @Test
    public void testSetCourse() throws Exception {
        coursePreRequisite.setCourse(new Course("name", "university", "department", "instructor", Integer.valueOf(0)));
    }

    @Test
    public void testSetCoursePreReq() throws Exception {
        coursePreRequisite.setCoursePreReq(new Course("name", "university", "department", "instructor", Integer.valueOf(0)));
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = coursePreRequisite.equals("o");
        Assert.assertEquals(true, result);
    }

    @Test
    public void testCanEqual() throws Exception {
        boolean result = coursePreRequisite.canEqual("other");
        Assert.assertEquals(true, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = coursePreRequisite.hashCode();
        Assert.assertEquals(0, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
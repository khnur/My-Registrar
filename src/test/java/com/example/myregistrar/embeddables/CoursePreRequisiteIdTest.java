package com.example.myregistrar.embeddables;

import org.junit.Assert;
import org.junit.Test;

public class CoursePreRequisiteIdTest {
    CoursePreRequisiteId coursePreRequisiteId = new CoursePreRequisiteId(1L, 1L);

    @Test
    public void testSetCourseId() throws Exception {
        coursePreRequisiteId.setCourseId(1L);
    }

    @Test
    public void testSetCoursePreReqId() throws Exception {
        coursePreRequisiteId.setCoursePreReqId(1L);
    }

    @Test
    public void testCanEqual() throws Exception {
        boolean result = coursePreRequisiteId.canEqual("other");
        Assert.assertFalse(result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = coursePreRequisiteId.hashCode();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void testToString() throws Exception {
        String result = coursePreRequisiteId.toString();
        Assert.assertEquals(coursePreRequisiteId.toString(), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
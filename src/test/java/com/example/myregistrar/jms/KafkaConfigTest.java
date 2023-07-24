package com.example.myregistrar.jms;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

public class KafkaConfigTest {
    KafkaConfig kafkaConfig = new KafkaConfig();

    @Test
    public void testCourseCreationTopic() throws Exception {
        NewTopic result = kafkaConfig.courseCreationTopic();
        Assert.assertNull(result);
    }

    @Test
    public void testKafkaListenerContainerFactory() throws Exception {
        ConcurrentKafkaListenerContainerFactory<String, String> result = kafkaConfig.kafkaListenerContainerFactory(null);
        Assert.assertNull(result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
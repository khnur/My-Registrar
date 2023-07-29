package com.example.myregistrar.jms;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.services.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {
    private static final String TOPIC = "my-topic";
    private static final String GROUP_ID = "${spring.kafka.consumer.group-id}";

    private final KafkaTemplate<Long, CourseDto> kafkaTemplate;
    private final StudentService studentService;

    public void sendToCourseTopic(CourseDto courseDto) {
        if (courseDto == null) {
            log.error("provided course is null");
            return;
        } else if (courseDto.getUniversity() == null) {
            log.error("course with id={} is not registered by any university", courseDto.getId());
            return;
        }
        kafkaTemplate.send(TOPIC, courseDto);
    }

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void listenMessage(CourseDto courseDto) {
        log.info(">>> Received: created new course " + courseDto.toJson());

        final int[] studentIter = new int[]{1};
        studentService.getAllStudents().forEach(student -> {
            log.info("Student with the following credentials received course enrolment request: {} {}",
                    studentIter[0]++, student.toStudentDto().toJson());
            if (student.getAge() >= 25) {
                studentService.assignCourseToStudent(student, courseDto.toCourse());

                log.info("Student accepted course enrolment request");
            } else {
                log.warn("Student is not eligible to enrol the course");
            }
        });
    }

    @Bean
    public NewTopic courseCreationTopic() {
        return TopicBuilder.name(TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}

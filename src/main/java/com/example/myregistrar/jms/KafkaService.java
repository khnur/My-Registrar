package com.example.myregistrar.jms;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.util.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.myregistrar.jms.KafkaConfig.COURSE_CREATION_TOPIC;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final StudentService studentService;

    public void sendToCourseTopic(String object) {
        kafkaTemplate.send(COURSE_CREATION_TOPIC, object);
    }

    @KafkaListener(topics = COURSE_CREATION_TOPIC, groupId = "groupId")
    public void listenMessage(String courseDtoJson) {
        log.info(">>> Received: created new course " + courseDtoJson);

        final int[] studentIter = new int[]{1};
        studentService.getAllStudents().forEach(student -> {
            log.info("Student with the following credentials received course enrolment request: {} {}",
                    studentIter[0]++, student.toStudentDto().toJson());
            if (student.getAge() >= 25) {
                CourseDto courseDto = JsonMapper.toObject(courseDtoJson, CourseDto.class);
                log.info("Student accepted course enrolment request");

                studentService.assignCourseToStudent(student, courseDto.getId());
            } else {
                log.warn("Student is not eligible to enrol the course");
            }
        });
    }

}

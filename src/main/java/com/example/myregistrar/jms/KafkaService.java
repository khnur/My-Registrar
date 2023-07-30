package com.example.myregistrar.jms;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.models.Course;
import com.example.myregistrar.models.University;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.services.UniversityService;
import com.example.myregistrar.util.entity_dto_mappers.CourseMapper;
import com.example.myregistrar.util.entity_dto_mappers.StudentMapper;
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
    private final UniversityService universityService;

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
        University university = universityService.getUniversityById(courseDto.getUniversity().getId());

        final int[] studentIter = new int[]{1};
        studentService.getStudentsByUniversity(university).forEach(student -> {

            StudentDto studentDto = StudentMapper.INSTANCE.studentToStudentDto(student);
            log.info("Student with the following credentials received course enrolment request: {} {}",
                    studentIter[0]++, studentDto.toJson());

            if (student.getAge() >= 25) {

                Course course = CourseMapper.INSTANCE.courseDtoToCourse(courseDto);
                studentService.assignCourseToStudent(student, course);

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

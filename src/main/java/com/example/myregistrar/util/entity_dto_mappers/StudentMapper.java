package com.example.myregistrar.util.entity_dto_mappers;

import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.models.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "student.courses", target = "courseDtoList")
    StudentDto studentToStudentDto(Student student);

    @Mapping(source = "studentDto.courseDtoList", target = "courses")
    Student studentDtoToStudent(StudentDto studentDto);

    List<StudentDto> studentListToStudentDtoList(List<Student> studentList);

    List<Student> studentDtoListToStudentList(List<StudentDto> studentDtoList);
}

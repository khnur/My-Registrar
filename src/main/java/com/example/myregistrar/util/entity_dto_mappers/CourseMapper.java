package com.example.myregistrar.util.entity_dto_mappers;

import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.models.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(source = "course.students", target = "studentDtoList")
    @Mapping(source = "course.books", target = "bookDtoList")
    CourseDto courseToCourseDto(Course course);

    @Mapping(source = "courseDto.studentDtoList", target = "students")
    @Mapping(source = "courseDto.bookDtoList", target = "books")
    Course courseDtoToCourse(CourseDto courseDto);

    List<CourseDto> courseListToCourseDtoList(List<Course> courseList);

    List<Course> courseDtoListToCoutseList(List<CourseDto> courseDtoList);
}

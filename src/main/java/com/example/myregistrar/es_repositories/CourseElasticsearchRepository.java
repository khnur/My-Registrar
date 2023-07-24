package com.example.myregistrar.es_repositories;

import com.example.myregistrar.models.Course;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseElasticsearchRepository extends ElasticsearchRepository<Course, Long> {
}

package com.example.myregistrar.es_repositories;

import com.example.myregistrar.models.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StudentElasticsearchRepository extends ElasticsearchRepository<Student, Long> {

}

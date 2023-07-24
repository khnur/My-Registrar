package com.example.myregistrar.es_repositories;

import com.example.myregistrar.models.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookElasticsearchRepository extends ElasticsearchRepository<Book, Long> {
}

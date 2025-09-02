package com.agp.geek.repositories;

import org.springframework.data.elasticsearch.annotations.Query;
import com.agp.geek.documents.TopicElastic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TopicRepositoryElastic extends ElasticsearchRepository<TopicElastic, String> {

    @Query("""
        {
          "bool": {
            "should": [
              {
                "wildcard": {
                  "title": {
                    "value": "*?0*",
                    "case_insensitive": true
                  }
                }
              }
            ],
            "minimum_should_match": 1
          }
        }
    """)
    List<TopicElastic> buscarTopico(String valor, Pageable pageable);

}

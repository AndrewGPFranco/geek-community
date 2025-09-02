package com.agp.geek.documents;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Builder
@Document(indexName = "topics")
public class TopicElastic {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;

}

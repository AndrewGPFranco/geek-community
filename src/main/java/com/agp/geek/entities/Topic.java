package com.agp.geek.entities;

import com.agp.geek.enums.TagType;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "topics")
public class Topic {

    @Id
    private String id;

    private String title;

    private String description;

    private List<TagType> tags;

    private String emailCreator;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Topic(String title, String description, List<TagType> tags, String emailCreator, LocalDateTime createAt, LocalDateTime updateAt) {
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.emailCreator = emailCreator;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", emailCreator='" + emailCreator + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}

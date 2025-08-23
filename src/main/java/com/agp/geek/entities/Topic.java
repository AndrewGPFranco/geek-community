package com.agp.geek.entities;

import com.agp.geek.enums.TagType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Table("topics")
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("tags")
    private List<TagType> tags;

    @Column("email_creator")
    private String emailCreator;

    @Column("created_at")
    private LocalDate createdAt;

    @Column("updated_at")
    private LocalDate updatedAt;

    @Override
    public String toString() {
        return "Topic{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", emailCreator='" + emailCreator + '\'' +
                ", createAt=" + createdAt +
                ", updateAt=" + updatedAt +
                '}';
    }
}

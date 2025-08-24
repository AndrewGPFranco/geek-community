package com.agp.geek.documents;

import com.agp.geek.enums.TagType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

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

    @Column("username")
    private String username;

    @Column("created_at")
    private LocalDate createdAt;

    @Column("updated_at")
    private LocalDate updatedAt;

    @Column("comments")
    private List<Comment> comments;

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", username='" + username + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", comments=" + comments +
                '}';
    }
}

package com.agp.geek.documents;

import com.agp.geek.enums.TagType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.Instant;
import java.util.List;

@Data
@Table("topics")
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    @PrimaryKey
    private String id;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("tags")
    private List<TagType> tags;

    @Column("username")
    private String username;

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    @Column("created_at")
    private Instant createdAt;

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    @Column("updated_at")
    private Instant updatedAt;

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

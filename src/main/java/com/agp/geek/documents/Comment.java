package com.agp.geek.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType("comment")
public class Comment {

    private String id;

    private String message;

    private String username;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

package com.agp.geek.dtos.topic;

import com.agp.geek.documents.Comment;
import com.agp.geek.enums.TagType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;

public record OutputTopicDTO(
        @NotBlank(message = "O ID não pode ser vazio")
        String id,

        @NotBlank(message = "O título não pode ser vazio")
        String title,

        @NotBlank(message = "A descrição não pode ser vazia")
        String description,

        @NotEmpty(message = "A lista de tags não pode estar vazia")
        List<TagType> tags,

        @NotNull(message = "A data de criação não pode ser nula")
        Instant createdAt,

        @NotNull(message = "A data de atualização não pode ser nula")
        Instant updatedAt,

        @NotNull(message = "A lista de comentários não pode ser nula")
        List<Comment> comments,

        @NotBlank(message = "O nome do criador não pode ser vazio")
        String usernameCreator
) {
}
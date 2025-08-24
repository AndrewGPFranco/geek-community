package com.agp.geek.dtos.topic;

import com.agp.geek.enums.TagType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record OutputTopicDTO(
        @NotNull UUID id,
        @NotBlank String title,
        @NotBlank String description,
        @NotEmpty List<TagType> tags,
        @NotNull LocalDate createdAt,
        @NotNull LocalDate updatedAt
) {}

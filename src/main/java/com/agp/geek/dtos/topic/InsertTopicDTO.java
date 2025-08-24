package com.agp.geek.dtos.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record InsertTopicDTO(
        @NotBlank(message = "É necessário informar um título!") String title,
        @NotBlank(message = "É necessário informar uma descrição!") String description,
        @NotEmpty(message = "É necessário informar ao menos 1 Tag!") List<String> tags
) {}

package com.agp.geek.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record ChangeDescriptionDTO(
        @NotBlank(message = "É necessário passar uma descrição válida!") String description
) {}

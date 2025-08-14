package com.agp.geek.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordDTO(
        @NotBlank String email
) {}

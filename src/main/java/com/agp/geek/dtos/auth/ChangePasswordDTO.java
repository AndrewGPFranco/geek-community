package com.agp.geek.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDTO(
    @NotBlank String newPassword
) {}

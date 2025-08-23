package com.agp.geek.dtos.auth;

import com.agp.geek.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ValidateCodeDTO(
        @NotBlank String uuid,
        @NotNull Integer code,
        User user
) {
}

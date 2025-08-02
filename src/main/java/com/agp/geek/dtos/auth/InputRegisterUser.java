package com.agp.geek.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record InputRegisterUser(
        @NotBlank String nome,
        @NotBlank String email,
        @NotBlank String senha,
        @NotBlank String nomeCompleto,
        @NotNull LocalDate dataNascimento,
        @NotBlank String apelido,
        @NotEmpty Set<String> roles
) {
}

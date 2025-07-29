package com.agp.ead.dtos.auth;

import com.agp.ead.enums.RoleType;

import java.time.LocalDate;
import java.util.Set;

public record InputRegisterUser(
        String nome,
        String email,
        String senha,
        String nomeCompleto,
        LocalDate dataNascimento,
        String apelido,
        Set<RoleType> roles
) {
}

package com.agp.ead.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    ALUNO("Aluno"),
    PROFESSOR("Professor"),
    MODERADOR("Moderador"),
    ADMIN("Administrador");

    private final String descricao;

    RoleType(String descricao) {
        this.descricao = descricao;
    }
}

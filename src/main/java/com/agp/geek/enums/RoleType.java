package com.agp.geek.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    LEITOR("Leitor"),
    ESCRITOR("Escritor"),
    MODERADOR("Moderador"),
    ADMINISTRADOR("Administrador");

    private final String descricao;

    RoleType(String descricao) {
        this.descricao = descricao;
    }
}

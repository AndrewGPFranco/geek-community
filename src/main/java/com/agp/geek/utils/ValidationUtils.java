package com.agp.geek.utils;

import jakarta.validation.constraints.NotBlank;

import java.util.regex.Pattern;

/**
 * Classe centralizadora de validações do sistema
 */
public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-z0-9.+]+@[a-z0-9]+\\.[a-z]+(\\.[a-z]+)?$",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$"
    );

    public static void validEmailAndPassword(@NotBlank String email, @NotBlank String password) throws IllegalArgumentException {
        if (!EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("O Email informado não segue o padrão!");

        if (!PASSWORD_PATTERN.matcher(password).matches())
            throw new IllegalArgumentException("A senha informada não segue o padrão pedido!");
    }

}

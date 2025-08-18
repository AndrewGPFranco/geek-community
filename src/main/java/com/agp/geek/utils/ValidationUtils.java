package com.agp.geek.utils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
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

    public static void validEmailAndPasswordAndAge(@NotBlank String email, @NotBlank String password,
                                                   @NotNull LocalDate dateBirth) throws IllegalArgumentException {
        LocalDate today = LocalDate.now();
        LocalDate tenYearsAgo = today.minusYears(10);

        if (dateBirth.isAfter(today))
            throw new IllegalArgumentException("Data de nascimento inválida!");

        if (dateBirth.isAfter(tenYearsAgo))
            throw new IllegalArgumentException("A idade mínima para utilizar o site é 10 anos!");

        if (!EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("O Email informado não segue o padrão!");

        if (!PASSWORD_PATTERN.matcher(password).matches())
            throw new IllegalArgumentException("A senha informada não segue o padrão pedido!");
    }

}

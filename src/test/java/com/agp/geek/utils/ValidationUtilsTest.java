package com.agp.geek.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class ValidationUtilsTest {

    private String email = "teste@gmail.com";
    private String password = "Teste2*";
    private LocalDate dateBirth = LocalDate.now().minusYears(15);

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidPassword() {
        password = "testep";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ValidationUtils.validEmailAndPasswordAndAge(email, password, dateBirth));

        String expected = "A senha informada não segue o padrão pedido!";
        String result = exception.getMessage();

        assertEquals(expected, result);
    }

    @Test
    void testeValidEmail() {
        email = "testegmail.com";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ValidationUtils.validEmailAndPasswordAndAge(email, password, dateBirth));

        String expected = "O Email informado não segue o padrão!";
        String result = exception.getMessage();

        assertEquals(expected, result);
    }

    @Test
    void testeValidDateBirth() {
        dateBirth = dateBirth.plusYears(10);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ValidationUtils.validEmailAndPasswordAndAge(email, password, dateBirth));

        String expected = "A idade mínima para utilizar o site é 10 anos!";
        String result = exception.getMessage();

        assertEquals(expected, result);
    }

    @Test
    void testeValidDateBirthInvalid() {
        dateBirth = dateBirth.plusYears(20);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ValidationUtils.validEmailAndPasswordAndAge(email, password, dateBirth));

        String expected = "Data de nascimento inválida!";
        String result = exception.getMessage();

        assertEquals(expected, result);
    }

}

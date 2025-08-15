package com.agp.geek.services;

import com.agp.geek.entities.User;
import com.agp.geek.enums.RoleType;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jwtService, "secretKey", "test-secret-key");
    }

    @Test
    void testeGeradorDeTokenETestValidaTokenJWT() {
        String result = jwtService.geradorDeTokenJWT(getUsuario());
        assertNotNull(result);

        String resultDois = jwtService.validaToken(result);
        assertNotNull(resultDois);
    }

    @Test
    void testeValidaTokenInvalidoComErro() {
        JWTVerificationException exception = assertThrows(JWTVerificationException.class, () -> jwtService.validaToken("tokeninvalido"));

        String esperado = "Ocorreu um erro ao validar o token";
        String resultado = exception.getMessage();

        assertEquals(esperado, resultado);
    }

    @Test
    void testeValidaTokenExpirado() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnZWVrLWFncCIsInN1YiI6IkFwZWxpZG8iLCJpZCI6ImQxY" +
                "zIzMDhmLTBhNjMtNDhiYi05Y2EyLWYzZTdhYmU4NTc1MSIsImVtYWlsIjoidGVzdGVAZ21haWwuY29tIiwicm9sZXMiOlsiTGVpdG" +
                "9yIl0sImV4cCI6MTc1NDI3MDQwMn0.gAWAWJUD2R0a_1HLcsUmwD2ktQkLH-5kIhID0m1-Ur4";

        TokenExpiredException exception = assertThrows(TokenExpiredException.class, () -> jwtService.validaToken(token));

        String esperado = "O token não esta mais válido!";
        String resultado = exception.getMessage();

        assertEquals(esperado, resultado);
    }

    private User getUsuario() {
        return User.builder()
                .id(UUID.randomUUID())
                .nome("Teste")
                .identificador("Identificador")
                .dataNascimento(LocalDate.of(2001, Month.JUNE, 10))
                .email("teste@gmail.com")
                .nomeCompleto("Teste Completo")
                .roles(Set.of(RoleType.LEITOR))
                .senha("testesenha")
                .build();
    }
}

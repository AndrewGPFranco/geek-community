package com.agp.geek.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Spy
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static Stream<Arguments> getMensagensDeErroRecuperada() {
        return Stream.of(
                Arguments.arguments(
                        "could not execute statement [ERROR: duplicate key value violates " +
                                "unique constraint \\\"users_email_key\\\"\\n  Detalhe: Key (email)=(andrewgo@gmail.com) already exists.] " +
                                "[insert into users (apelido,data_nascimento,email,nome,nome_completo,senha,id) values (?,?,?,?,?,?,?)]; " +
                                "SQL [insert into users (apelido,data_nascimento,email,nome,nome_completo,senha,id) values (?,?,?,?,?,?,?)]; " +
                                "constraint [users_email_key]",
                        "O email informado já esta em uso!"
                ),
                Arguments.arguments(
                        "could not execute statement [ERROR: duplicate key value violates unique constraint \"users_apelido_key\"\n" +
                                "  Detalhe: Key (apelido)=(Gongon) already exists.] [insert into users (apelido,data_nascimento,email,nome,nome_completo,senha,id)" +
                                " values (?,?,?,?,?,?,?)]; SQL [insert into users (apelido,data_nascimento,email,nome,nome_completo,senha,id)" +
                                " values (?,?,?,?,?,?,?)]; constraint [users_apelido_key]",
                        "O apelido informado já esta em uso!"
                ),
                Arguments.arguments(
                        "Qualquer outra mensagem errada",
                        "Ocorreu um erro ao registrar o usuário, verifique os dados e tente novamente!"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getMensagensDeErroRecuperada")
    void testeRecoversFieldAlreadyUsed(String mensagem, String esperado) {
        String result = authService.recoversFieldAlreadyUsed(mensagem);
        assertEquals(esperado, result);
    }
}
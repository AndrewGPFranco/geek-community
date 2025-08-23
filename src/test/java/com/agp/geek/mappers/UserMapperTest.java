package com.agp.geek.mappers;

import com.agp.geek.dtos.auth.InputRegisterUserDTO;
import com.agp.geek.entities.User;
import com.agp.geek.enums.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        this.userMapper = Mockito.spy(new UserMapper(bcrypt));
    }

    @Test
    void testDtoParaEntidade() {
        InputRegisterUserDTO input = getInputRegisterUserDTO();

        User result = userMapper.dtoParaEntidade(input);

        assertNotNull(result);
        assertEquals(input.nome(), result.getNome());
        assertEquals(input.email(), result.getEmail());
        assertNotEquals(input.senha(), result.getSenha());
        assertEquals(input.identificador(), result.getIdentificador());
        assertEquals(input.nomeCompleto(), result.getNomeCompleto());
        assertEquals(input.dataNascimento(), result.getDataNascimento());
        assertEquals(input.roles().iterator().next(), result.getRoles().iterator().next().getDescricao());
    }

    @Test
    void testDevolveRolesTratadas() {
        Set<String> rolesString = Set.of("Leitor", "Escritor", "Moderador", "Administrador");
        Set<RoleType> roleTypes = Set.of(RoleType.values());

        Set<RoleType> result = userMapper.devolveRolesTratadas(rolesString);

        assertEquals(roleTypes, result);
    }


    private InputRegisterUserDTO getInputRegisterUserDTO() {
        return new InputRegisterUserDTO(
                "Teste",
                "teste@gmail.com",
                "passteste",
                "Teste Completo",
                LocalDate.of(2025, Month.JUNE, 28),
                "Apelido",
                Set.of(RoleType.LEITOR.getDescricao())
        );
    }
}

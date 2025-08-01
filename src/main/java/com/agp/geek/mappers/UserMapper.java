package com.agp.geek.mappers;

import com.agp.geek.dtos.auth.InputRegisterUser;
import com.agp.geek.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User dtoParaEntidade(InputRegisterUser dto) {
        return User.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(bCryptPasswordEncoder.encode(dto.senha()))
                .nomeCompleto(dto.nomeCompleto())
                .dataNascimento(dto.dataNascimento())
                .apelido(dto.apelido())
                .roles(dto.roles())
                .build();
    }

}

package com.agp.ead.mappers;

import com.agp.ead.dtos.auth.InputRegisterUser;
import com.agp.ead.entities.User;
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

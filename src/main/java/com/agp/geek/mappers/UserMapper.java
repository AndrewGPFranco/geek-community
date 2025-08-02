package com.agp.geek.mappers;

import com.agp.geek.dtos.auth.InputRegisterUser;
import com.agp.geek.entities.User;
import com.agp.geek.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

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
                .roles(devolveRolesTratadas(dto.roles()))
                .build();
    }

    private Set<RoleType> devolveRolesTratadas(Set<String> roles) {
        return roles.stream().map(role -> RoleType.valueOf(role.toUpperCase())).collect(Collectors.toSet());
    }

}

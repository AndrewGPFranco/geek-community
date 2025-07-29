package com.agp.ead.services;

import com.agp.ead.dtos.auth.InputRegisterUser;
import com.agp.ead.entities.User;
import com.agp.ead.mappers.UserMapper;
import com.agp.ead.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public void registerUser(InputRegisterUser inputDTO) {
        try {
            User user = userMapper.dtoParaEntidade(inputDTO);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

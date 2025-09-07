package com.agp.geek.services;

import com.agp.geek.dtos.user.InfoProfileDTO;
import com.agp.geek.entities.User;
import com.agp.geek.enums.RoleType;
import com.agp.geek.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void changeDescriptionUser(String description, User user) {
        if (!description.isEmpty()) {
            user.setDescription(description);
            userRepository.save(user);
        } else
            throw new IllegalArgumentException("A descrição não pode estar vazia!");
    }

    public InfoProfileDTO getInfosUser(User user) {
        Optional<RoleType> role = user.getRoles().stream().findFirst();

        return role.map(roleType -> new InfoProfileDTO(user.getUsername(), user.getDescription(),
                roleType.getDescricao(), user.getCreatedAt())).orElseGet(() -> new InfoProfileDTO(user.getUsername(),
                user.getDescription(), "", user.getCreatedAt()));
    }
}

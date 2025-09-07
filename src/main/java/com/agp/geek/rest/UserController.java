package com.agp.geek.rest;

import com.agp.geek.dtos.user.ChangeDescriptionDTO;
import com.agp.geek.dtos.user.InfoProfileDTO;
import com.agp.geek.entities.User;
import com.agp.geek.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/user/change-description")
    ResponseEntity<String> changeDescriptionUser(@Valid @RequestBody ChangeDescriptionDTO dto, @AuthenticationPrincipal User user) {
        userService.changeDescriptionUser(dto.description(), user);
        return ResponseEntity.ok().body("Descrição alterada com sucesso!");
    }

    @GetMapping("/api/v1/user/info-profile")
    ResponseEntity<InfoProfileDTO> getInfosUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(userService.getInfosUser(user));
    }

}

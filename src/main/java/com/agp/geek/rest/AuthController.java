package com.agp.geek.rest;

import com.agp.geek.dtos.auth.ChangePasswordDTO;
import com.agp.geek.dtos.auth.ForgotPasswordDTO;
import com.agp.geek.dtos.auth.InputRegisterUserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agp.geek.dtos.ResponseAPI;
import com.agp.geek.dtos.auth.LoginRequestDTO;
import com.agp.geek.entities.User;
import com.agp.geek.services.AuthService;
import com.agp.geek.services.JwtService;
import com.auth0.jwt.exceptions.TokenExpiredException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;
    private final AuthenticationManager authManager;

    @PostMapping("/user/login")
    ResponseEntity<ResponseAPI> login(@RequestBody LoginRequestDTO dto) {
        try {
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(),
                    dto.password());

            Authentication auth = this.authManager.authenticate(usernamePassword);
            String token = jwtService.geradorDeTokenJWT((User) auth.getPrincipal());

            return ResponseEntity.ok().body(new ResponseAPI(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new ResponseAPI(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseAPI("Ocorreu um erro ao processar o login!"));
        }
    }

    @PostMapping("/user/register")
    ResponseEntity<String> registerUser(@RequestBody @Valid InputRegisterUserDTO inputDTO) {
        authService.registraUsuario(inputDTO);
        return ResponseEntity.ok().body("Usuário registrado com sucesso!");
    }

    @GetMapping("/valid-token/{token}")
    ResponseEntity<Boolean> tokenIsValid(@PathVariable String token) {
        try {
            if (token == null || token.equals("null") || token.isEmpty())
                return ResponseEntity.ok().body(false);

            jwtService.validaToken(token);
            return ResponseEntity.ok().body(true);
        } catch (TokenExpiredException e) {
            // Retornando status 200 pois não é um erro, foi somente uma verificação!
            return ResponseEntity.status(200).body(false);
        }
    }

    @PostMapping("/user/change-password")
    ResponseEntity<String> changePassword(@AuthenticationPrincipal User user, @Valid @RequestBody ChangePasswordDTO input) {
        authService.changePassword(user, input);
        return ResponseEntity.ok().body("Senha alterada com sucesso!");
    }

    @PostMapping("/user/forgot-password")
    ResponseEntity<String> forgotPasswordInviteEmail(@Valid @RequestBody ForgotPasswordDTO input) {
        authService.inviteLinkForgotPassword(input.email());
        return ResponseEntity.ok().body("Email para alteração de senha enviado!");
    }

}

package com.agp.geek.services;

import com.agp.geek.entities.User;
import com.agp.geek.enums.RoleType;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class JwtService {

    @Value("${JWT:default-secret}")
    private String secretKey;

    public String geradorDeTokenJWT(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("geek-agp")
                    .withSubject(user.getIdentificador())
                    .withClaim("id", user.getId().toString())
                    .withClaim("email", user.getEmail())
                    .withClaim("roles", user.getRoles().stream().map(RoleType::getDescricao).toList())
                    .withExpiresAt(dataExpiracaoDoToken())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            log.error(e.getMessage());
            throw new JWTCreationException("Erro ao criar o token", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar o token");
        }
    }

    public String validaToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm)
                    .withIssuer("geek-agp")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException("O token não esta mais válido!", e.getExpiredOn());
        } catch (JWTVerificationException e) {
            log.error(e.getMessage());
            throw new JWTVerificationException("Ocorreu um erro ao validar o token");
        }
    }

    private Instant dataExpiracaoDoToken() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }

}

package com.agp.geek.services;

import com.agp.geek.entities.User;
import com.agp.geek.enums.RoleType;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    @Value("${JWT:default-secret}")
    private String secretKey;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("geek-agp")
                    .withSubject(user.getApelido())
                    .withClaim("id", user.getId().toString())
                    .withClaim("email", user.getEmail())
                    .withClaim("roles", user.getRoles().stream().map(RoleType::getDescricao).toList())
                    .withExpiresAt(tokenExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Erro ao criar o token", e);
        }
    }

    public String validateToken(String token) {
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
            throw new JWTVerificationException("Ocorreu um erro ao validar o token", e);
        }
    }

    private Instant tokenExpirationDate() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }

}

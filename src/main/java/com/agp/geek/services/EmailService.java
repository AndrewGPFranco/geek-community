package com.agp.geek.services;

import com.agp.geek.components.CacheComponent;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final CacheComponent cacheComponent;

    public void enviarEmailParaAlteracaoSenha(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject(subject);
            message.setText(body);

            UUID uuid = UUID.randomUUID();

            cacheComponent.savedUUIDForgotPassword(uuid, to);

            String htmlContent = "<h1>Link para alteração da senha!</h1>" +
                    "<a href=\"http://localhost:3000/auth/forgot-password/" + uuid + "\">Alterar senha</a>";
            message.setContent(htmlContent, "text/html; charset=utf-8");

            mailSender.send(message);
            log.info("Email enviado com sucesso para: {}", to);
        } catch (Exception e) {
            log.error("Erro ao enviar email para: {}", to, e);
            throw new RuntimeException("Falha no envio do email", e);
        }
    }

    public String validaUUID(@NotBlank String uuid) {
        return cacheComponent.recoversCache(UUID.fromString(uuid));
    }
}

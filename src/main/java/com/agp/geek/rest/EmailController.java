package com.agp.geek.rest;

import com.agp.geek.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/enviar")
    public String enviarEmail(@RequestParam String para,
                              @RequestParam String assunto,
                              @RequestParam String corpo) {
        emailService.enviarEmailParaAlteracaoSenha(para, assunto, corpo);
        return "Email enviado com sucesso!";
    }

}

package com.agp.geek.dtos.topic;

import jakarta.validation.constraints.NotBlank;

public record InsertCommentDTO(
        @NotBlank(message = "É necessário informar uma mensagem ao comentário!") String message,
        @NotBlank(message = "O ID do tópico é obrigatório!") String idTopic
) {}

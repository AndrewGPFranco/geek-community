package com.agp.geek.dtos.topic;

import jakarta.validation.constraints.NotBlank;

public record DeleteCommentDTO(
        @NotBlank(message = "É necessário informar o id do comentário a ser removido!") String idComment,
        @NotBlank(message = "É necessário informar o id do tópico a qual o comentário pertence!") String idTopic
) {}

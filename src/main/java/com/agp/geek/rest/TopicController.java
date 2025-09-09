package com.agp.geek.rest;

import com.agp.geek.dtos.topic.InsertCommentDTO;
import com.agp.geek.dtos.topic.InsertTopicDTO;
import com.agp.geek.dtos.topic.OutputTopicDTO;
import com.agp.geek.dtos.topic.ScreenNewTopicDTO;
import com.agp.geek.entities.User;
import com.agp.geek.services.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/open/v1/topic/all")
    ResponseEntity<List<OutputTopicDTO>> getAllTopics(@RequestParam("pageSize") Integer pageSize,
                                                      @RequestParam("pageNumber") Integer pageNumber) {
        List<OutputTopicDTO> allTopics = topicService.getAllTopics(pageSize, pageNumber);
        return ResponseEntity.ok().body(allTopics);
    }

    @PostMapping("/api/v1/topic")
    @PreAuthorize("hasAuthority('ESCRITOR')")
    ResponseEntity<String> createNewTopic(@RequestBody @Valid InsertTopicDTO dto, @AuthenticationPrincipal User user) {
        topicService.createANewTopic(dto, user);
        return ResponseEntity.ok().body("Tópico inserido com sucesso!");
    }

    @DeleteMapping("/api/v1/topic/delete-all")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    ResponseEntity<String> deleteAll() {
        topicService.deleteAllTopics();
        return ResponseEntity.ok().body("Tópicos removidos com sucesso!");
    }

    @PreAuthorize("hasAuthority('LEITOR')")
    @PostMapping("/api/v1/topic/add-comment")
    ResponseEntity<String> addCommentToTopic(@RequestBody @Valid InsertCommentDTO dto, @AuthenticationPrincipal User user) {
        topicService.addComment(dto, user.getUsername());
        return ResponseEntity.ok().body("Comentário adicionado!");
    }

    @GetMapping("/open/v1/topic/by-id")
    ResponseEntity<OutputTopicDTO> topicByID(@RequestParam("id") String id) {
        OutputTopicDTO topic = topicService.getTopicById(id);
        return ResponseEntity.ok().body(topic);
    }

    @GetMapping("/open/v1/topic/search")
    ResponseEntity<List<OutputTopicDTO>> searchTopic(@RequestParam("query") String query) {
        List<OutputTopicDTO> topicList = topicService.searchTopic(query);
        return ResponseEntity.ok().body(topicList);
    }

    @GetMapping("/api/v1/itens-topic")
    ResponseEntity<ScreenNewTopicDTO> getItensToCreateTopic() {
        return ResponseEntity.ok().body(topicService.getItensToCreateTopic());
    }

}

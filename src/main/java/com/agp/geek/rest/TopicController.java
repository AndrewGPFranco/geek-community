package com.agp.geek.rest;

import com.agp.geek.dtos.topic.InsertTopicDTO;
import com.agp.geek.dtos.topic.OutputTopicDTO;
import com.agp.geek.entities.User;
import com.agp.geek.services.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic")
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/all")
    ResponseEntity<List<OutputTopicDTO>> getAllTopics(@RequestParam("pageSize") Integer pageSize,
                                                      @RequestParam("pageNumber") Integer pageNumber) {
        List<OutputTopicDTO> allTopics = topicService.getAllTopics(pageSize, pageNumber);
        return ResponseEntity.ok().body(allTopics);
    }

    @PostMapping
    ResponseEntity<String> createNewTopic(@RequestBody @Valid InsertTopicDTO dto, @AuthenticationPrincipal User user) {
        topicService.createANewTopic(dto, user.getEmail());
        return ResponseEntity.ok().body("Tópico inserido com sucesso!");
    }

    @DeleteMapping("/delete-all")
    ResponseEntity<String> deleteAll() {
        topicService.deleteAllTopics();
        return ResponseEntity.ok().body("Tópicos removidos com sucesso!");
    }

}

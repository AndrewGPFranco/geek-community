package com.agp.geek.rest;

import com.agp.geek.entities.Topic;
import com.agp.geek.services.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topic")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/all")
    ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> allTopics = topicService.getAllTopics();
        return ResponseEntity.ok().body(allTopics);
    }

    @PostMapping("/insert")
    ResponseEntity<String> insertNewTopic() {
        topicService.insertTopic();
        return ResponseEntity.ok().body("Tópico inserido com sucesso!");
    }

}

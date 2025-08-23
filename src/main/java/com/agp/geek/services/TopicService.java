package com.agp.geek.services;

import com.agp.geek.entities.Topic;
import com.agp.geek.enums.TagType;
import com.agp.geek.repositories.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public void insertTopic() {
        topicRepository.save(new Topic(
            "Primeiro tópico",
                "Primeira descrição",
                List.of(TagType.ANIME, TagType.DESENHO),
                "andrewgomes1328@gmail.com",
                LocalDateTime.now(),
                null
        ));
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

}

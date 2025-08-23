package com.agp.geek.services;

import com.agp.geek.entities.Topic;
import com.agp.geek.enums.TagType;
import com.agp.geek.repositories.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public void insertTopic() {
        topicRepository.save(new Topic(
                UUID.randomUUID(),
                "Primeiro tópico",
                "Primeira descrição",
                List.of(TagType.ANIME, TagType.DESENHO),
                "andrewgomes1328@gmail.com",
                LocalDate.now(),
                null
        ));
    }

    public void deleteAllTopics() {
        topicRepository.deleteAll();
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

}

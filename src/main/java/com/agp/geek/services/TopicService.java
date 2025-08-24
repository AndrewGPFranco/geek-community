package com.agp.geek.services;

import com.agp.geek.documents.Comment;
import com.agp.geek.dtos.topic.InsertCommentDTO;
import com.agp.geek.dtos.topic.InsertTopicDTO;
import com.agp.geek.dtos.topic.OutputTopicDTO;
import com.agp.geek.documents.Topic;
import com.agp.geek.entities.User;
import com.agp.geek.enums.RoleType;
import com.agp.geek.exceptions.NotFoundException;
import com.agp.geek.mappers.TopicMapper;
import com.agp.geek.repositories.TopicRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicMapper topicMapper;
    private final TopicRepository topicRepository;

    public void createANewTopic(InsertTopicDTO dto, User user) {
        if (!user.getRoles().contains(RoleType.ESCRITOR))
            throw new RuntimeException("Para criar um tópico é necessário ser um escritor no site!");

        topicRepository.save(topicMapper.dtoToEntity(dto, user.getUsername()));
    }

    public void deleteAllTopics() {
        topicRepository.deleteAll();
    }

    public List<OutputTopicDTO> getAllTopics(Integer pageSize, Integer pageNumber) {
        Slice<Topic> topics = topicRepository.findAll(CassandraPageRequest.of(pageNumber, pageSize));
        return topics.getContent().stream().map(topicMapper::entityToOutputTopicDTO).toList();
    }

    public void addComment(@Valid InsertCommentDTO dto, String username) {
        Topic topic = topicRepository.findById(dto.idTopic()).orElseThrow(() ->
                new NotFoundException("Tópico com id " + dto.idTopic() + " não encontrado!"));

        if (topic.getComments() == null) topic.setComments(new ArrayList<>());
        topic.getComments().add(new Comment(UUID.randomUUID().toString(), dto.message(), username));

        topicRepository.save(topic);
    }
}

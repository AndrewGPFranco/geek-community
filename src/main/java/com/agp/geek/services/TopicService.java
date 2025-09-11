package com.agp.geek.services;

import com.agp.geek.documents.Comment;
import com.agp.geek.documents.TopicElastic;
import com.agp.geek.dtos.topic.*;
import com.agp.geek.documents.Topic;
import com.agp.geek.entities.User;
import com.agp.geek.enums.RoleType;
import com.agp.geek.enums.TagType;
import com.agp.geek.exceptions.NotFoundException;
import com.agp.geek.mappers.TopicMapper;
import com.agp.geek.repositories.TopicRepository;
import com.agp.geek.repositories.TopicRepositoryElastic;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicMapper topicMapper;
    private final TopicRepository topicRepository;
    private final TopicRepositoryElastic topicRepositoryElastic;

    public void createANewTopic(InsertTopicDTO dto, User user) {
        if (!user.getRoles().contains(RoleType.ESCRITOR))
            throw new RuntimeException("Para criar um tópico é necessário ser um escritor no site!");

        Topic entity = topicMapper.dtoToEntity(dto, user.getUsername());
        topicRepository.save(entity);
        topicRepositoryElastic.save(TopicElastic.builder().id(entity.getId()).title(dto.title()).build());
    }

    public void deleteAllTopics() {
        topicRepository.deleteAll();
        topicRepositoryElastic.deleteAll();
    }

    public List<OutputTopicDTO> getAllTopics(Integer pageSize, Integer pageNumber) {
        Slice<Topic> topics = topicRepository.findAll(CassandraPageRequest.of(pageNumber, pageSize));
        return topics.getContent().stream().map(topicMapper::entityToOutputTopicDTO).toList();
    }

    public void addComment(@Valid InsertCommentDTO dto, String username) {
        Topic topic = topicRepository.findById(dto.idTopic())
                .orElseThrow(() -> new NotFoundException("Tópico com id " + dto.idTopic() + " não encontrado!"));

        if (topic.getComments() == null)
            topic.setComments(new ArrayList<>());
        topic.getComments().add(new Comment(UUID.randomUUID().toString(), dto.message(), username));

        topicRepository.save(topic);
    }

    public OutputTopicDTO getTopicById(String idTopic) {
        Topic topic = topicRepository.findById(idTopic).orElseThrow(
                () -> new NotFoundException(String.format("Tópico com o ID %s não foi encontrado", idTopic)));

        return topicMapper.entityToOutputTopicDTO(topic);
    }

    public List<OutputTopicDTO> searchTopic(String query) {
        Pageable pageable = PageRequest.of(0, 10);
        List<TopicElastic> topicElastics = topicRepositoryElastic.buscarTopico(query, pageable);

        List<OutputTopicDTO> outputTopicDTOS = new ArrayList<>(topicElastics.size());
        for (TopicElastic topic : topicElastics) {
            Optional<Topic> byId = topicRepository.findById(topic.getId());

            byId.ifPresent(value -> outputTopicDTOS.add(topicMapper.entityToOutputTopicDTO(value)));
        }

        return outputTopicDTOS;
    }

    public ScreenNewTopicDTO getItensToCreateTopic() {
        List<String> tags = Arrays.stream(TagType.values()).map(TagType::getDescription).toList();
        return new ScreenNewTopicDTO(tags);
    }

    public Integer amountTopicWrittenByUser(String username) {
        return topicRepository.amountTopicWrittenByUser(username);
    }

    public String deleteComment(String username, DeleteCommentDTO dto) {
        Topic topic = topicRepository.findById(String.valueOf(dto.idTopic())).orElseThrow(
                () -> new NotFoundException("Tópico com ID: " + dto.idTopic() + " não encontrado!"));

        Optional<Comment> comment = topic.getComments().stream().filter(c -> c.getId()
                .equals(dto.idComment())).findFirst();

        if (comment.isEmpty())
            throw new NotFoundException("Comentário com ID: " + dto.idComment() + " não encontrado!");

        if (!comment.get().getUsername().equals(username))
            throw new IllegalArgumentException("Usuário informado não é o proprietário do comentário");

        topic.getComments().remove(comment.get());

        topicRepository.save(topic);

        return "Comentário removido com sucesso!";
    }
}

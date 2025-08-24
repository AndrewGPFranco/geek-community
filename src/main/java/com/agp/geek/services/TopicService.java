package com.agp.geek.services;

import com.agp.geek.dtos.topic.InsertTopicDTO;
import com.agp.geek.dtos.topic.OutputTopicDTO;
import com.agp.geek.entities.Topic;
import com.agp.geek.mappers.TopicMapper;
import com.agp.geek.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicMapper topicMapper;
    private final TopicRepository topicRepository;

    public void createANewTopic(InsertTopicDTO dto, String emailCreator) {
        topicRepository.save(topicMapper.dtoToEntity(dto, emailCreator));
    }

    public void deleteAllTopics() {
        topicRepository.deleteAll();
    }

    public List<OutputTopicDTO> getAllTopics(Integer pageSize, Integer pageNumber) {
        Slice<Topic> topics = topicRepository.findAll(CassandraPageRequest.of(pageNumber, pageSize));
        return topics.getContent().stream().map(topicMapper::entityToOutputTopicDTO).toList();
    }

}

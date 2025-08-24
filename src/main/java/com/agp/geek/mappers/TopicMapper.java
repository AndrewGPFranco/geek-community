package com.agp.geek.mappers;

import com.agp.geek.dtos.topic.InsertTopicDTO;
import com.agp.geek.dtos.topic.OutputTopicDTO;
import com.agp.geek.entities.Topic;
import com.agp.geek.enums.TagType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class TopicMapper {

    public Topic dtoToEntity(InsertTopicDTO dto, String emailCreator) {
        return new Topic(UUID.randomUUID(), dto.title(), dto.description(), TagType.getTagsFromStrings(dto.tags()), emailCreator,
                LocalDate.now(), null);
    }

    public OutputTopicDTO entityToOutputTopicDTO(Topic entity) {
        return new OutputTopicDTO(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getTags(),
                entity.getCreatedAt(), entity.getUpdatedAt());
    }

}

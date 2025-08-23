package com.agp.geek.repositories;

import com.agp.geek.entities.Topic;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends CassandraRepository<Topic, String> {}
package com.agp.geek.repositories;

import com.agp.geek.documents.Topic;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends CassandraRepository<Topic, String> {

    @Query("select count(*) from topics where username = :username ALLOW FILTERING" )
    Integer amountTopicWrittenByUser(@Param("username") String username);

}
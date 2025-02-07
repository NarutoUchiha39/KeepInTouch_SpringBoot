package com.Messaging.messaging.Repository;

import com.Messaging.messaging.Models.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages,Long> {

    @Query(value = "SELECT * FROM messages where (from_id=:from AND to_id=:to) OR (to_id=:from AND from_id=:to) ", nativeQuery = true)
    public List<Messages>getAllMessages(@Param("from") Long from, @Param("to") Long to);
}

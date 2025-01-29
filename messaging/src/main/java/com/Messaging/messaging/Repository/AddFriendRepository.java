package com.Messaging.messaging.Repository;

import com.Messaging.messaging.Models.FriendRequest;
import com.Messaging.messaging.Models.FriendRequestKey;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddFriendRepository extends JpaRepository<FriendRequest, FriendRequestKey> {

    @Query(value = "SELECT * from friend_request where from_id=:from",nativeQuery = true)
    List<FriendRequest> findAllSentRequest(@Param("from")Long from);
    
    @Query(value = "SELECT * from friend_request where to_id=:to",nativeQuery = true)
    List<FriendRequest>findAllReceivedtRequest(@PathParam("to") Long to);
}

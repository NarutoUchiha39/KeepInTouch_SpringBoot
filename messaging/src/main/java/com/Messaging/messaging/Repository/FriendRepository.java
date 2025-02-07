package com.Messaging.messaging.Repository;

import com.Messaging.messaging.Models.FriendRequestKey;
import com.Messaging.messaging.Models.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friends, FriendRequestKey> {

    @Query(value = "SELECT * FROM friends WHERE to_id =:userid OR from_id=:userid",nativeQuery = true)
    public List<Friends> getAllFriends(@Param("userid") Long userid);
}

package com.Messaging.messaging.Repository;

import com.Messaging.messaging.Models.FriendRequest;
import com.Messaging.messaging.Models.FriendRequestKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddFriendRepository extends JpaRepository<FriendRequest, FriendRequestKey> {
}

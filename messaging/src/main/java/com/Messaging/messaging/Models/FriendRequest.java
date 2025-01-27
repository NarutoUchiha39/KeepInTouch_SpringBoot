package com.Messaging.messaging.Models;

import jakarta.persistence.*;
import org.Model.shared.User;



@Table(name = "friend_request")
@Entity
public class FriendRequest {

    @EmbeddedId
    FriendRequestKey friendRequestKey;

    public FriendRequestKey getFriendRequestKey() {
        return friendRequestKey;
    }

    public void setFriendRequestKey(FriendRequestKey friendRequestKey) {
        this.friendRequestKey = friendRequestKey;
    }

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @MapsId("to")
    @JoinColumn(name = "to_id", referencedColumnName = "userid")
    User to;


    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @MapsId("from")
    @JoinColumn(name = "from_id", referencedColumnName = "userid")
    User from;

    @Column(name = "status")
    String status;

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FriendRequest() {}
}

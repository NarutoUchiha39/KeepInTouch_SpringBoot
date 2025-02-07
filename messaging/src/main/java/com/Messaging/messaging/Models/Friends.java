package com.Messaging.messaging.Models;

import jakarta.persistence.*;
import org.Model.shared.User;
import org.hibernate.annotations.Generated;

@Table(name = "friends")
@Entity
public class Friends {

    @EmbeddedId
    FriendRequestKey friendRequestKey;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @MapsId("from")
    @JoinColumn(name = "from_id",referencedColumnName = "userid")
    public User from;


    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @MapsId("to")
    @JoinColumn(name = "to_id",referencedColumnName = "userid")
    public User to;


    public Friends() {}

    public FriendRequestKey getFriendRequestKey() {
        return friendRequestKey;
    }

    public void setFriendRequestKey(FriendRequestKey friendRequestKey) {
        this.friendRequestKey = friendRequestKey;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }


    public void setTo(User to) {
        this.to = to;
    }

    public Friends(FriendRequestKey friendRequestKey, User from, User to) {
        this.friendRequestKey = friendRequestKey;
        this.from = from;
        this.to = to;
    }
}

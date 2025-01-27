package com.BootProject.Project.Models;

import jakarta.persistence.*;

import java.io.Serializable;


@Embeddable
class FriendRequestKey implements Serializable {
    @Column(name = "to_id")
    Long to;

    @Column(name = "from_id")
    Long from;

    public FriendRequestKey(Long to, Long from) {
        this.to = to;
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }
}

@Table(name = "friend_request")
@Entity
public class FriendRequest {

    @EmbeddedId
    FriendRequestKey friendRequestKey;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @MapsId("toID")
    @JoinColumn(name = "to_id", referencedColumnName = "userid")
    User to;


    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @MapsId("fromID")
    @JoinColumn(name = "from_id", referencedColumnName = "userid")
    User from;

    public FriendRequest() {}
}

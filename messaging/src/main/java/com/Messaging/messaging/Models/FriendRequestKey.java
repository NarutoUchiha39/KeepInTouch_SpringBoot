package com.Messaging.messaging.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FriendRequestKey implements Serializable {
    @Column(name = "to_id")
    Long to;

    @Column(name = "from_id")
    Long from;

    public FriendRequestKey() {
    }

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

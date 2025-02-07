package com.Messaging.messaging.Class;

public class GetFriendRequestID {
    public Long id;
    public Long to;

    public GetFriendRequestID(Long id, Long from) {
        this.id = id;
        this.to = from;
    }

    public GetFriendRequestID() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long from) {
        this.to = from;
    }
}

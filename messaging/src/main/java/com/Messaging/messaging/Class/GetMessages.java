package com.Messaging.messaging.Class;

public class GetMessages {

    public Long to;
    public Long from;
    public String message;
    public String Link;

    public GetMessages(Long to, Long from, String message, String link) {
        this.to = to;
        this.from = from;
        this.message = message;
        Link = link;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}

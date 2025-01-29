package com.Messaging.messaging.Class;

public class ChangeRequestStatus {

    public Long from;
    public Long to;
    String Status;

    public ChangeRequestStatus(Long from, Long to, String status) {
        this.from = from;
        this.to = to;
        Status = status;
    }

    public String getStatus() {
        return Status;
    }

    public ChangeRequestStatus() {
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }
}

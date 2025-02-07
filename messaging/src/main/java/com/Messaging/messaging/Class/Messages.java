package com.Messaging.messaging.Class;


import com.Messaging.messaging.Models.Friends;
import org.Model.shared.User;
import org.springframework.web.multipart.MultipartFile;

public class Messages {
    public String from;
    public String to;
    public String message;
    public MultipartFile file;

    public Messages(String from, String to, String message, MultipartFile file) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.file = file;
    }

    public Messages() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}


package com.Messaging.messaging.Models;

import jakarta.persistence.*;
import org.Model.shared.User;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Table(name = "messages")
@Entity
public class Messages {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Column(name = "messages",columnDefinition = "TEXT")
    public String messages;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "from_id",referencedColumnName = "userid")
    public User from;


    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "to_id",referencedColumnName = "userid")
    public User to;

    @Column(name = "link")
    @ColumnDefault(" 'None' ")
    public String link;

    public Messages(){}

    public Messages(Long id, String messages) {
        this.id = id;
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
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
}

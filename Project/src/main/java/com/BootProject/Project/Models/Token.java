package com.BootProject.Project.Models;

import jakarta.persistence.*;

@Table(name = "token")
@Entity
public class Token {

    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long token_id;

    public void setToken_id(Long token_id) {
        this.token_id = token_id;
    }

    public Token(){}
    public Token(Long token_id, String token, Boolean loggedOut, User user_id) {
        this.token_id = token_id;
        this.token = token;
        this.loggedOut = loggedOut;
        this.userid = user_id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLoggedOut(Boolean loggedOut) {
        this.loggedOut = loggedOut;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    @Column(name = "token", columnDefinition = "TEXT")
    public String token;

    public Long getToken_id() {
        return token_id;
    }

    public String getToken() {
        return token;
    }

    public Boolean getLoggedOut() {
        return loggedOut;
    }

    public User getUserid() {
        return userid;
    }

    @Column(name="is_logged_out")
    public Boolean loggedOut;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "userid",referencedColumnName = "userid")
    public User userid;
}

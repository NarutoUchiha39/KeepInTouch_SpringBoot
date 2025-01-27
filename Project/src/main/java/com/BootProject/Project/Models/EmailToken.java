package com.BootProject.Project.Models;

import jakarta.persistence.*;

@Table(name = "email_token")
@Entity
public class EmailToken {
    public String getUuid() {
        return uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public EmailToken(){}

    public EmailToken(String uuid, String email, String username, String password, Long exp, Long id, String profileLink) {
        this.uuid = uuid;
        this.email = email;
        this.username = username;
        this.password = password;
        this.exp = exp;
        this.id = id;
        this.profileLink = profileLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }


    @Column(name = "uuid")
    String uuid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email",unique = true)
    public String email;

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    @Column(name = "profile_link")
    public String profileLink;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;

    @Column(name = "exp")
    public Long exp;

}

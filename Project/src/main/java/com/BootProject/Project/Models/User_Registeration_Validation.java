package com.BootProject.Project.Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User_Registeration_Validation {
    private String email;
    private String password;
    private String username;
    private String profileLink;

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    @JsonCreator
    public User_Registeration_Validation(@JsonProperty(value = "email", required = true) String email,
                                         @JsonProperty(value = "password", required = true) String password,
                                         @JsonProperty(value = "username", required = true) String username,
                                         @JsonProperty(value = "profile_link", required = true) String profileLink) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.profileLink = profileLink;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

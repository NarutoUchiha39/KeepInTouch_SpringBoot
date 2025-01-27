package org.Model.shared;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Table(name = "user_profile")
@Entity
public class User {
    public Long getUserID() {
        return userID;
    }

    @Override
    public String toString(){
        return "Username: " + this.username + "password: "+this.password+"email: "+this.email;
    }

    public User(){}

    public User(Long userID, String email, String username, String password) {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Column(name = "profile_link")
    public String profileLink;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    public Long userID;

    @Column(name = "email",unique = true)


    public String email;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;


    public String getPassword() {
        return this.password;
    }


    public String getUsername() {
        return this.username;
    }


    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return true;
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return true;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

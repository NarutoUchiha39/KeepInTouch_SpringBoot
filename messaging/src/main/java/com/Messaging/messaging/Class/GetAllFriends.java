package com.Messaging.messaging.Class;

import com.Messaging.messaging.Models.Friends;

import java.util.List;

public class GetAllFriends {
    public GetAllFriends() {
    }

    List<Friends>friendsList;
    Integer statusCode;

    public GetAllFriends(List<Friends> friendsList, Integer statusCode) {
        this.friendsList = friendsList;
        this.statusCode = statusCode;
    }

    public List<Friends> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<Friends> friendsList) {
        this.friendsList = friendsList;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}

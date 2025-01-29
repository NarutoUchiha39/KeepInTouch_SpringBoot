package com.Messaging.messaging.Response;

import com.Messaging.messaging.Models.FriendRequest;

import java.util.List;

public class GenericListOfRequests {

    List<FriendRequest>AllRequests;
    Integer statusCode;

    public GenericListOfRequests() {
    }

    public GenericListOfRequests(List<FriendRequest> allRequests, Integer statusCode) {
        AllRequests = allRequests;
        this.statusCode = statusCode;
    }

    public List<FriendRequest> getAllRequests() {
        return AllRequests;
    }

    public void setAllRequests(List<FriendRequest> allRequests) {
        AllRequests = allRequests;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}

package com.Messaging.messaging.Response;

import org.Model.shared.User;

public class UserDTOResponse {

    User use;
    Integer statusCode;

    public UserDTOResponse(User use, Integer statusCode) {
        this.use = use;
        this.statusCode = statusCode;
    }

    public UserDTOResponse() {}

    public User getUse() {
        return use;
    }

    public void setUse(User use) {
        this.use = use;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}

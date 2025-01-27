package com.BootProject.Project.Classes;

import com.BootProject.Project.Models.User;

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

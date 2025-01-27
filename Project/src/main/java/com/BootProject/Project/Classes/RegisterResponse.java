package com.BootProject.Project.Classes;


public class RegisterResponse {

    String message;
    Integer statusCode;
    String token;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RegisterResponse(){}

    public RegisterResponse(String message, Integer statusCode, String token) {
        this.message = message;
        this.statusCode = statusCode;
        this.token = token;
    }

}

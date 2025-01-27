package com.Messaging.messaging.Response;

public class GenericResponse {

    public String message;
    public Integer statusCode;

    public GenericResponse(){}
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

    public GenericResponse(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}

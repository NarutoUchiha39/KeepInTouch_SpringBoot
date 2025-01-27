package com.BootProject.Project.Models;

import jakarta.validation.constraints.NotNull;

public class User_Login_Validation {

    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "Password cannot be null")
    private String password;


    public User_Login_Validation(String email,
                                 String password) {
        this.email = email;
        this.password = password;
    }


    public User_Login_Validation(){}

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
}

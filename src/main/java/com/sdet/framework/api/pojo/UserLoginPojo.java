package com.sdet.framework.api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginPojo {

    private String email;
    private String password;

    // 1. Default (No-Args) Constructor
    public UserLoginPojo() {
    }

    // 2. Parameterized Constructor
    public UserLoginPojo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // 3. Getters and Setters
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
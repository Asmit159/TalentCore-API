package com.luv2code.springboot.cruddemo.security;

public class AuthRequest {

    private String username;
    private String password;

    public AuthRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
}

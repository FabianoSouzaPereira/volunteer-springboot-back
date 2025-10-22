package com.fabianospdev.volunteer.dto;

import java.io.Serializable;

public class LoginResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token; // JWT ou outro token de sessão
    private String userId;
    private String name;
    private String email;

    public LoginResponseDTO() { }

    public LoginResponseDTO(String token, String userId, String name, String email) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

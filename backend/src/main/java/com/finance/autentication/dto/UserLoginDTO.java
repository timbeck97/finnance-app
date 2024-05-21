package com.finance.autentication.dto;


import com.finance.autentication.model.User;

import java.util.stream.Collectors;

public class UserLoginDTO {
    private String name;
    private String username;
    private String roles="";
    private String token;
    private String refreshToken;

    public UserLoginDTO(User user, String token, String refreshToken) {
        this.name = user.getName();
        this.username = user.getUsername();
        this.roles=this.roles.join(", ", user.getRoles().stream().map(x->x.getName()).collect(Collectors.toList()));
        this.token=token;
        this.refreshToken=refreshToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

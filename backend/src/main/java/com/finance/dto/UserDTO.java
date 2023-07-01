/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.dto;

import com.finance.model.User;

import java.util.stream.Collectors;

/**
 *
 * @author tim
 */
public class UserDTO {

    private String name;

    private String lastName;
    private String username;

    private String email;

    private String roles="";

    public UserDTO(User user) {

        this.name = user.getName();
        this.username = user.getUsername();
        this.roles=this.roles.join(", ", user.getRoles().stream().map(x->x.getName()).collect(Collectors.toList()));
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

}

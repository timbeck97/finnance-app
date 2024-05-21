/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.autentication.dto;

import com.finance.autentication.model.User;

import java.util.stream.Collectors;

/**
 *
 * @author tim
 */
public class UserDTO {

  private long id;
    private String name;

    private String username;

    private String email;

    private String roles="";

    public UserDTO(User user) {
        this.id=user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.email=user.getEmail();
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}

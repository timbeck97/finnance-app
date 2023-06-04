/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.service;


import com.finance.model.Role;
import com.finance.model.User;

import java.util.List;

/**
 *
 * @author tim
 */
public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    List<User> getUsers();
    User getUser(String username);
}

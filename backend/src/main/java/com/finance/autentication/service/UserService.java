/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.autentication.service;


import com.finance.autentication.dto.ChangePasswordDTO;
import com.finance.autentication.dto.ReceiveUserDTO;
import com.finance.autentication.dto.UserDTO;
import com.finance.autentication.model.Role;
import com.finance.autentication.model.User;

import java.util.List;

/**
 *
 * @author tim
 */
public interface UserService {
    User saveUser(ReceiveUserDTO user);

    User updateUser(ReceiveUserDTO user, Long id);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    List<User> getUsers();
    User getUser(String username);

    User changePassword(ChangePasswordDTO dto);

    User getUsuarioLogado();
}

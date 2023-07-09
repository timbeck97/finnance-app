/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.service;



import com.finance.dto.ChangePasswordDTO;
import com.finance.model.Role;
import com.finance.model.User;
import com.finance.repository.RoleRepository;
import com.finance.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 * @author tim
 */
@Service
@Transactional
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final Utils utils;

    private static Logger log = LoggerFactory.getLogger(UserServiceImp.class);


    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, Utils utils){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;
      this.utils = utils;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user==null){
            log.info("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        log.info("User found in the database: {}",username);

        Collection<SimpleGrantedAuthority> autorities = new ArrayList<>();
        user.getRoles().forEach(role->autorities.add(new SimpleGrantedAuthority(role.getName() )));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),autorities);
    }
    @Override
    public User saveUser(User user) {
        log.info("Saving user {} to database",user.getUsername());
        Role role=roleRepository.findByName("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

  @Override
  public User updateUser(User user) {
    log.info("Updating user {} to database",user.getUsername());
    User oldUser=userRepository.findById(user.getId()).get();
    oldUser.setEmail(user.getEmail());
    oldUser.setName(user.getName());
    oldUser.setUsername(user.getUsername());
    return userRepository.save(oldUser);
  }

  @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {}",role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}",roleName, username);
        Role role=roleRepository.findByName(roleName);
        User user=userRepository.findByUsername(username);
        user.getRoles().add(role);

        //userRepository.save(user);

    }

    @Override
    public List<User> getUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

  @Override
  public User changePassword(ChangePasswordDTO dto) {
    User user=utils.getUsuarioLogado();
    if(!passwordEncoder.matches(dto.getOldPassword(),user.getPassword())){
      throw new IllegalArgumentException("Senha antiga incorreta");
    }
    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    return userRepository.save(user);
  }


}

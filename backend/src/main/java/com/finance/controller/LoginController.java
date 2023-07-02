/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.dto.UserDTO;
import com.finance.model.Role;
import com.finance.model.User;
import com.finance.service.UserService;
import com.finance.service.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 *
 * @author tim
 */
@RestController
public class LoginController {

    private final UserService userService;

     private final Utils util;

    @Value("${token.expiration.minutes}")
    private String tokenExpiration;
    @Value("${jwt.secret}")
    private String secret;

    public LoginController(UserService userService, Utils util) {
        this.userService = userService;
      this.util = util;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDTO>> getUsers(){
       List<UserDTO> dtos= userService.getUsers().stream().map(user->new UserDTO(user)).collect(Collectors.toList());
       return ResponseEntity.ok().body(dtos);
    }
    @GetMapping(value = "/users/me")
    public ResponseEntity<UserDTO> getUser(){
      User usuarioLogado = util.getUsuarioLogado();
      UserDTO dtos= Optional.of(userService.getUser(usuarioLogado.getUsername())).map(user->new UserDTO(user)).orElse(null);
       return ResponseEntity.ok().body(dtos);
    }
    @PostMapping(value = "/signin")
    public ResponseEntity<UserDTO> saveUser(@RequestBody User user){
        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(new UserDTO(userService.saveUser(user)));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/role/addtouser")

    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/token/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
            Long token_exp=System.currentTimeMillis()+(Integer.valueOf(4)*60*1000);
            String refresh_token=request.getHeader("Authorization");
            String token=refresh_token.split("Bearer")[1].trim();


            if(refresh_token !=null){
//                try{
                    Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                    JWTVerifier verifier=JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);

                    String username=decodedJWT.getSubject();
                    String isRefreshToken=decodedJWT.getClaim("refreshToken").as(String.class);
                    if(!Boolean.parseBoolean(isRefreshToken)){
                        throw new RuntimeException("Forbbiden revalidate token with normal token");
                    }
                    User user=userService.getUser(username);
                    String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(token_exp))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                    Map<String, String> tokens=new HashMap<>();
                    tokens.put("access_token", access_token);
                    tokens.put("access_token_expiration",String.valueOf(token_exp));
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);

//                }catch(Exception e){
////                    response.setHeader("error", e.getMessage());
////                    response.setStatus(HttpStatus.FORBIDDEN.value());
////                    Map<String, String> erros=new HashMap<>();
////                    erros.put("error_message", e.getMessage());
////                    response.setContentType(APPLICATION_JSON_VALUE);
////                    new ObjectMapper().writeValue(response.getOutputStream(), erros);
//
//                }
            }else{
                throw new RuntimeException("Refresh token is missing");
            }
    }


}

class RoleToUserForm{
        private String username;
        private String roleName;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }


    }

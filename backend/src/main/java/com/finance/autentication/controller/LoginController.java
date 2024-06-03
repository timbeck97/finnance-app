/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.autentication.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.autentication.dto.*;
import com.finance.autentication.model.Role;
import com.finance.autentication.model.User;
import com.finance.autentication.security.AutenticationService;
import com.finance.autentication.service.UserService;
import com.finance.configuration.Utils;
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
 * @author tim
 */
@RestController
public class LoginController {

  private final UserService userService;
  private final Utils util;
  private final AutenticationService autenticationService;


  @Value("${token.expiration.minutes}")
  private String tokenExpiration;
  @Value("${jwt.secret}")
  private String secret;

  public LoginController(UserService userService, Utils util, AutenticationService autenticationService) {
    this.userService = userService;
    this.util = util;
    this.autenticationService = autenticationService;
  }

  @GetMapping(value = "/users")
  public ResponseEntity<List<UserDTO>> getUsers() {
    List<UserDTO> dtos = userService.getUsers().stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
    return ResponseEntity.ok().body(dtos);
  }

  @GetMapping(value = "/users/me")
  public ResponseEntity<UserDTO> getUser() {
    User usuarioLogado = util.getUsuarioLogado();
    UserDTO dtos = Optional.of(userService.getUser(usuarioLogado.getUsername())).map(user -> new UserDTO(user)).orElse(null);
    return ResponseEntity.ok().body(dtos);
  }

  @PostMapping(value = "/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO user, HttpServletRequest request, HttpServletResponse response){
    return ResponseEntity.ok().body(autenticationService.autenticate(user, request, response));
  }

  @PostMapping(value = "/signin")
  public ResponseEntity<UserDTO> saveUser(@RequestBody ReceiveUserDTO user) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
    return ResponseEntity.created(uri).body(new UserDTO(userService.saveUser(user)));
  }

  @PutMapping(value = "/users/{id}")
  public ResponseEntity<UserDTO> updateUser(@RequestBody ReceiveUserDTO user, @PathVariable Long id) {
    return ResponseEntity.ok(new UserDTO(userService.updateUser(user, id)));
  }

  @PostMapping(value = "/users/changePassword")
  public ResponseEntity<UserDTO> changePassword(@RequestBody ChangePasswordDTO dto) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/changePassword").toUriString());

    return ResponseEntity.created(uri).body(new UserDTO(userService.changePassword(dto)));
  }


  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(value = "/role")
  public ResponseEntity<Role> saveRole(@RequestBody Role role) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
    return ResponseEntity.created(uri).body(userService.saveRole(role));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(value = "/role/addtouser")

  public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
    userService.addRoleToUser(form.getUsername(), form.getRoleName());
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/token/refreshtoken")
  public void refreshToken(@RequestBody Map mapToken, HttpServletRequest request, HttpServletResponse response) throws IOException {
    Long token_exp = System.currentTimeMillis() + (Integer.valueOf(4) * 60 * 1000);
    String refreshToken = (String) mapToken.get("refreshToken");
    if (refreshToken != null) {
      Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT decodedJWT = verifier.verify(refreshToken);

      String username = decodedJWT.getSubject();
      String isRefreshToken = decodedJWT.getClaim("refreshToken").as(String.class);
      if (!Boolean.parseBoolean(isRefreshToken)) {
        throw new RuntimeException("Forbbiden revalidate token with normal token");
      }
      User user = userService.getUser(username);
      String access_token = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(token_exp))
        .withIssuer(request.getRequestURL().toString())
        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
        .sign(algorithm);

      Map<String, String> tokens = new HashMap<>();
      tokens.put("access_token", access_token);
      tokens.put("access_token_expiration", String.valueOf(token_exp));
      response.setContentType(APPLICATION_JSON_VALUE);
      new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
  }


}


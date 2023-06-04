/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.dto.LoginDTO;
import com.finance.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 *
 * @author tim
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

     private UserRepository userRepository;
     private final AuthenticationManager authenticationManager;
     private final HandlerExceptionResolver handlerExceptionResolver;


     private String tokenExpiration;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, HandlerExceptionResolver handlerExceptionResolver, String tokenExpiration) {
        this.authenticationManager = authenticationManager;
        this.userRepository=userRepository;
        this.handlerExceptionResolver=handlerExceptionResolver;
        this.tokenExpiration=tokenExpiration;
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){

        LoginDTO usuario=new LoginDTO();
        try {

              usuario = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

         } catch (IOException ex) {
             Logger.getLogger(CustomAuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
         }

         UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());
        Authentication aut=null;
        try{
            aut=authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            handlerExceptionResolver.resolveException(request,response,null,e);
        }
        return aut;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        System.out.println("token expiration: "+tokenExpiration);
        //Long token_exp=System.currentTimeMillis()+(Integer.valueOf(tokenExpiration)*60*1000);
        Long token_exp=System.currentTimeMillis()+(Integer.valueOf(tokenExpiration)*60*1000);
        Long token_ref_exp=System.currentTimeMillis()+(Integer.valueOf(tokenExpiration)*2*60*1000);

        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(token_exp))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))

                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(token_ref_exp))
                .withClaim("refreshToken", Boolean.TRUE)
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);


        Map<String, String> tokens=new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("access_token_expiration", String.valueOf(token_exp));
        tokens.put("refresh_token_expiration",String.valueOf(token_ref_exp));
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        this.liberacaoCors(response);
        //UserLoginDTO dto=new UserLoginDTO(userRepository.findByUsername(user.getUsername()),access_token, refresh_token);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);


    }
     private void liberacaoCors(HttpServletResponse response) {
        if(response.getHeader("Access-Control-Allow-Origin")==null){
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        if(response.getHeader("Access-Control-Allow-Headers")==null){
            response.addHeader("Access-Control-Allow-Headers", "*");
        }
        if(response.getHeader("Access-Control-Request-Headers")==null){
            response.addHeader("Access-Control-Request-Headers", "*");
        }
        if(response.getHeader("Access-Control-Allow-Methods")==null){
            response.addHeader("Access-Control-Allow-Methods", "*");
        }

    }

}

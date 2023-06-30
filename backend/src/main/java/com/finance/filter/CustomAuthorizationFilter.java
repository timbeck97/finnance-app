/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.finance.exceptions.InvalidTokenException;
import com.finance.service.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 *
 * @author tim
 */
@Configuration
public class CustomAuthorizationFilter extends OncePerRequestFilter{

    private final HandlerExceptionResolver handlerExceptionResolver;

    private final String SECRET="finnanceAppTimMorgenstern";
  private static Logger log = LoggerFactory.getLogger(CustomAuthorizationFilter.class);
    public CustomAuthorizationFilter(HandlerExceptionResolver handlerExceptionResolver){
        this.handlerExceptionResolver=handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals("/login") || request.getServletPath().equals("/token/refreshtoken")){
            filterChain.doFilter(request, response);
        }else{
            String authorizationHeader=request.getHeader(AUTHORIZATION);
            if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
                try{
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
                    JWTVerifier verifier=JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username=decodedJWT.getSubject();
                    String isRefreshToken=decodedJWT.getClaim("refreshToken").as(String.class);
                    if(Boolean.parseBoolean(isRefreshToken)){
                        throw new RuntimeException("Forbbiden access with refresh token");
                    }

                    String[] roles= decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();

                    stream(roles).forEach(role->authorities.add(new SimpleGrantedAuthority(role)));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    response.addHeader("Access-Control-Expose-Headers","X-Total-Count");
                    filterChain.doFilter(request, response);

                }catch(SignatureVerificationException e){
                    log.error("Error loging in: {}",e.getMessage());
                    handlerExceptionResolver.resolveException(request,response,null,new InvalidTokenException("The token is not válid"));

                }
                catch(JWTDecodeException e){
                    handlerExceptionResolver.resolveException(request,response,null,new InvalidTokenException("The token is not a valid string"));
                }
                catch(TokenExpiredException e){
                    handlerExceptionResolver.resolveException(request,response,null,e);
                }

            }else{
                filterChain.doFilter(request, response);
            }
        }
    }


}

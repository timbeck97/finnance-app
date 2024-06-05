/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.autentication.security;




import com.finance.configuration.exceptions.InvalidTokenException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 *
 * @author tim
 */

public class CustomAuthorizationFilter extends OncePerRequestFilter{

    private final HandlerExceptionResolver handlerExceptionResolver;

    private String secret;
    private static Logger log = LoggerFactory.getLogger(CustomAuthorizationFilter.class);

    public CustomAuthorizationFilter(HandlerExceptionResolver handlerExceptionResolver, String secret){
        this.handlerExceptionResolver=handlerExceptionResolver;
        this.secret=secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String authorizationHeader=request.getHeader(AUTHORIZATION);
            if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
                try{
                  String token = authorizationHeader.substring("Bearer ".length());

                  Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

                  String username=claims.getBody().getSubject();
                  String isRefreshToken=claims.getBody().get("refreshToken",String.class);
                  if(Boolean.parseBoolean(isRefreshToken)){
                      throw new RuntimeException("Forbbiden access with refresh token");
                  }

                  ArrayList<String> roles=claims.getBody().get("roles",ArrayList.class);
                  Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();

                  roles.forEach(role->authorities.add(new SimpleGrantedAuthority(role)));
                  UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null, authorities);
                  authenticationToken.setDetails(claims);
                  SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                  response.addHeader("Access-Control-Expose-Headers","X-Total-Count");
                  filterChain.doFilter(request, response);

                }catch(SignatureException e){
                    log.error("Error loging in: {}",e.getMessage());
                    handlerExceptionResolver.resolveException(request,response,null,new InvalidTokenException("The token is not válid"));

                }

                catch(MalformedJwtException e){
                    handlerExceptionResolver.resolveException(request,response,null,new InvalidTokenException("The token is not a valid string"));
                }

                catch(ExpiredJwtException e){
                    handlerExceptionResolver.resolveException(request,response,null,e);
                }

            }else{
                filterChain.doFilter(request, response);
            }

    }


}

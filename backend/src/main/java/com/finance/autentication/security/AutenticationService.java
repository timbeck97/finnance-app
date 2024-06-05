package com.finance.autentication.security;


import com.finance.autentication.dto.LoginDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class AutenticationService {

  private final AuthenticationManager authenticationManager;

  @Value("${token.expiration.minutes}")
  private String tokenExpiration;
  @Value("${jwt.secret}")
  private String secret;

  public AutenticationService(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  public Map<String, String> autenticate( LoginDTO usuario, HttpServletRequest request, HttpServletResponse response) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());
    Authentication authentication = null;
    try {
      authentication = authenticationManager.authenticate(authenticationToken);
      User user = (User)authentication.getPrincipal();

      Long token_exp=System.currentTimeMillis()+(Integer.valueOf(tokenExpiration)*60*1000);
      Long token_ref_exp=System.currentTimeMillis()+(Integer.valueOf(tokenExpiration)*2*60*1000);


      String access_token= Jwts
        .builder()
        .setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(token_exp))
        .claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();


      String refresh_token= Jwts
        .builder()
        .setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(token_ref_exp))
        .claim("refreshToken", Boolean.TRUE)
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();


      Map<String, String> tokens=new HashMap<>();
      tokens.put("access_token", access_token);
      tokens.put("access_token_expiration", String.valueOf(token_exp));
      tokens.put("refresh_token_expiration",String.valueOf(token_ref_exp));
      tokens.put("refresh_token", refresh_token);
      response.setContentType(APPLICATION_JSON_VALUE);
      this.liberacaoCors(response);
      return tokens;
    } catch (BadCredentialsException e) {
      throw e;
    }


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
    response.addHeader("Access-Control-Expose-Headers","X-Total-Count");

  }
}

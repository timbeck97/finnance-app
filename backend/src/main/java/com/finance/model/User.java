/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author tim
 */
@Entity
@Table(name = "user_table")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="user_sequence")
    @SequenceGenerator(name="user_sequence", sequenceName="user_seq")
    private Long id;
    private String name;

    @Column(unique = true)
    private String username;
    private String password;

    @Column(columnDefinition = "varchar(60) not null default 'exemple@mail.com'")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    //---------------------------------------------------------

    @Column(columnDefinition = "numeric(14,2) not null default 0")
    private double contaCorrente;


    public User() {
    }

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public double getContaCorrente() {
    return contaCorrente;
  }

  public void setContaCorrente(double contaCorrente) {
    this.contaCorrente = contaCorrente;
  }
}

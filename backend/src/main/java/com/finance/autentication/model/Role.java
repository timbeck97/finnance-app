/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.autentication.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author tim
 */
@Entity
public class Role implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="role_sequence")
    @SequenceGenerator(name="role_sequence", sequenceName="role_seq")
    private Long id;
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
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


}

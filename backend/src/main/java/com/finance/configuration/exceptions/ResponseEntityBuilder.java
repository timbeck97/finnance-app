/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finance.configuration.exceptions;

import org.springframework.http.ResponseEntity;

/**
 *
 * @author tim
 */
public class ResponseEntityBuilder {

    public static ResponseEntity<Object> build(ApiError apiError) {

          return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

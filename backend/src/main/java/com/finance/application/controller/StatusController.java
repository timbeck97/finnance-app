package com.finance.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {

  @RequestMapping
  public String status() {
    return "OK";
  }
}


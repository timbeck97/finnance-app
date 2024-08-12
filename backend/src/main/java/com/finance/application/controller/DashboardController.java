package com.finance.application.controller;


import com.finance.application.dto.DashboardAnualDTO;
import com.finance.application.dto.DashboardMensalDTO;
import com.finance.application.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

  private final DashboardService dashboardService;

  public DashboardController(DashboardService dashboardService) {
    this.dashboardService = dashboardService;
  }

  @GetMapping("/mensal/{ano}/{mes}")
  public ResponseEntity<DashboardMensalDTO> getBarCharMensal(@PathVariable String ano, @PathVariable String mes) {
    return ResponseEntity.ok(dashboardService.getDashboardMensal(ano, mes));
  }
  @GetMapping("/anual/{ano}")
  public ResponseEntity<DashboardAnualDTO> getBarCharAnual(@PathVariable String ano) {
    return ResponseEntity.ok(dashboardService.getDashboardAnual(ano));
  }
}

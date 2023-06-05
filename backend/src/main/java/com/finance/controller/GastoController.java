package com.finance.controller;

import com.finance.model.Gasto;
import com.finance.repository.GastoRepository;
import com.finance.service.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/gastos")
public class GastoController {

  @Autowired
  private Utils util;

  @Autowired
  private GastoRepository gastoRepository;
  @GetMapping
  public ResponseEntity<List<Gasto>> findAll(
    @RequestParam(required = false) String anoMes,
    @RequestParam(required = false, defaultValue = "1")int pageNumber,
    @RequestParam(required = false, defaultValue = "5")int pageSize){
    if(anoMes==null){
      anoMes=util.getAnoMesAtual();
    }
    Pageable page=PageRequest.of(pageNumber-1, pageSize);
    Page<Gasto> gastos=gastoRepository.findByData(anoMes,util.getUsuarioLogado(),page);
    return ResponseEntity.ok(gastos.getContent());

  }
}

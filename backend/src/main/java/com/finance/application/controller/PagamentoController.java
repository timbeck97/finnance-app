package com.finance.application.controller;


import com.finance.application.dto.PagamentoDTO;
import com.finance.application.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

  private final PagamentoService pagamentoService;

  public PagamentoController(PagamentoService pagamentoService) {
    this.pagamentoService = pagamentoService;
  }

  @GetMapping(value = "/{data}")
  public ResponseEntity<PagamentoDTO> findPgamento(@PathVariable String data){
    return ResponseEntity.ok().body(pagamentoService.findByData(data));
  }
  @PostMapping
  public ResponseEntity<PagamentoDTO> savePagamento(@RequestBody @Valid  PagamentoDTO pagamentoDTO){
    return ResponseEntity.status(201).body(new PagamentoDTO(pagamentoService.save(pagamentoDTO)));
  }
  @PutMapping(value = "/{id}")
  public ResponseEntity<PagamentoDTO> updatePagamento(@PathVariable Long id,@RequestBody PagamentoDTO pagamentoDTO){
    return ResponseEntity.ok().body(new PagamentoDTO(pagamentoService.updatePagamento(pagamentoDTO,id)));
  }

}

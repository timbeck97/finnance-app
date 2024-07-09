package com.finance.application.controller;

import com.finance.application.dto.DepositoDTO;
import com.finance.application.service.DepositoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depositos")
public class DepositoController {

  private final DepositoService depositoService;

  public DepositoController(DepositoService depositoService) {
    this.depositoService = depositoService;
  }

  @GetMapping
  public ResponseEntity<List<DepositoDTO>> findAll(
    @RequestParam(required = false) String anoMes) {
    return ResponseEntity.ok(depositoService.findAll(anoMes));
  }

  @PostMapping
  public ResponseEntity<DepositoDTO> save(@RequestBody DepositoDTO dto) {
    return ResponseEntity.status(201).body(new DepositoDTO(depositoService.save(dto)));
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<DepositoDTO> update(@RequestBody DepositoDTO dto, @PathVariable long id) {
    return ResponseEntity.ok(new DepositoDTO(depositoService.update(dto, id)));
  }
}

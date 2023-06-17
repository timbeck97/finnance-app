package com.finance.controller;

import com.finance.dto.DepositoDTO;
import com.finance.dto.GastoDTO;
import com.finance.model.Deposito;
import com.finance.model.Gasto;
import com.finance.repository.DepositoRepository;
import com.finance.service.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depositos")
public class DepositoController {

  private DepositoRepository depositoRepository;
  private final Utils util;

  public DepositoController(DepositoRepository depositoRepository, Utils util) {
    this.depositoRepository = depositoRepository;
    this.util = util;
  }

  @GetMapping
  public ResponseEntity<List<DepositoDTO>> findAll(
    @RequestParam(required = false) String anoMes){
    if(anoMes==null){
      anoMes=util.getAnoMesAtual();
    }
    List<DepositoDTO> list=depositoRepository.findByUsuarioAndData(util.getUsuarioLogado(),anoMes);
    return ResponseEntity.ok(list);
  }
  @PostMapping
  public ResponseEntity<DepositoDTO> save(@RequestBody DepositoDTO dto){
    Deposito gasto=new Deposito();
    gasto.setData(dto.getData());
    gasto.setDescricao(dto.getDescricao());
    gasto.setUsuario(util.getUsuarioLogado());
    gasto.setValor(dto.getValor());
    return ResponseEntity.ok(new DepositoDTO(depositoRepository.save(gasto)));

  }
  @PutMapping(value = "/{id}")
  public ResponseEntity<DepositoDTO> save(@RequestBody DepositoDTO dto, @PathVariable long id){
    Deposito deposito=depositoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Recurso não encontrado"));
    deposito.setData(dto.getData());
    deposito.setDescricao(dto.getDescricao());
    deposito.setValor(dto.getValor());
    return ResponseEntity.ok(new DepositoDTO(depositoRepository.save(deposito)));
  }
}

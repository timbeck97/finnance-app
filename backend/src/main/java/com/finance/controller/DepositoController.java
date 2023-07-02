package com.finance.controller;

import com.finance.dto.DepositoDTO;
import com.finance.dto.GastoDTO;
import com.finance.exceptions.DataNotFoundException;
import com.finance.model.Deposito;
import com.finance.model.Gasto;
import com.finance.repository.DepositoRepository;
import com.finance.repository.GastoRepository;
import com.finance.service.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/depositos")
public class DepositoController {

  private DepositoRepository depositoRepository;

  private GastoRepository gastoRepository;
  private final Utils util;

  public DepositoController(DepositoRepository depositoRepository, GastoRepository gastoRepository, Utils util) {
    this.depositoRepository = depositoRepository;
    this.gastoRepository = gastoRepository;
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
    Deposito deposito=new Deposito();
    deposito.setData(dto.getData());
    deposito.setDescricao(dto.getDescricao());
    deposito.setUsuario(util.getUsuarioLogado());
    deposito.setValor(dto.getValor());
    deposito.setGastoVinculado(gastoRepository.findById(dto.getGastoVinculado()).orElse(null));


    return ResponseEntity.ok(new DepositoDTO(depositoRepository.save(deposito)));

  }
  @PutMapping(value = "/{id}")
  public ResponseEntity<DepositoDTO> save(@RequestBody DepositoDTO dto, @PathVariable long id){
    System.out.println("id");
    Deposito deposito=depositoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Recurso não encontrado"));
    deposito.setData(dto.getData());
    deposito.setDescricao(dto.getDescricao());
    deposito.setValor(dto.getValor());
    deposito.setGastoVinculado(gastoRepository.findById(dto.getGastoVinculado()).orElse(null));


    return ResponseEntity.ok(new DepositoDTO(depositoRepository.save(deposito)));
  }
}

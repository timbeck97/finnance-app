package com.finance.controller;

import com.finance.dto.DepositoDTO;
import com.finance.model.Deposito;
import com.finance.model.User;
import com.finance.repository.DepositoRepository;
import com.finance.repository.GastoRepository;
import com.finance.repository.UserRepository;
import com.finance.service.SaldoService;
import com.finance.service.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depositos")
public class DepositoController {

  private final DepositoRepository depositoRepository;

  private final GastoRepository gastoRepository;

  private final UserRepository userRepository;
  private final Utils util;

  private final SaldoService saldoService;

  public DepositoController(DepositoRepository depositoRepository, GastoRepository gastoRepository, Utils util, UserRepository userRepository, SaldoService saldoService) {
    this.depositoRepository = depositoRepository;
    this.gastoRepository = gastoRepository;
    this.util = util;
    this.userRepository=userRepository;
    this.saldoService = saldoService;
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
    deposito=depositoRepository.save(deposito);
    saldoService.atualizarSaldo(deposito);

    return ResponseEntity.ok(new DepositoDTO(deposito));

  }
  @PutMapping(value = "/{id}")
  public ResponseEntity<DepositoDTO> save(@RequestBody DepositoDTO dto, @PathVariable long id){
    System.out.println("id");
    Deposito deposito=depositoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Recurso não encontrado"));
    double valorAnterior=deposito.getValor();
    deposito.setData(dto.getData());
    deposito.setDescricao(dto.getDescricao());
    deposito.setValor(dto.getValor());
    deposito.setGastoVinculado(gastoRepository.findById(dto.getGastoVinculado()).orElse(null));
    deposito=depositoRepository.save(deposito);
    saldoService.atualizarSaldo(deposito);

    return ResponseEntity.ok(new DepositoDTO(deposito));
  }
}

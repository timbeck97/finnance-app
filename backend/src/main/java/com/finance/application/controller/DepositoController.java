package com.finance.application.controller;

import com.finance.application.builder.DepositoBuilder;
import com.finance.application.dto.DepositoDTO;
import com.finance.application.model.Deposito;
import com.finance.application.repository.DepositoRepository;
import com.finance.application.repository.GastoRepository;
import com.finance.autentication.repository.UserRepository;
import com.finance.application.service.SaldoService;
import com.finance.autentication.service.UserService;
import com.finance.configuration.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depositos")
public class DepositoController {

  private final DepositoRepository depositoRepository;

  private final GastoRepository gastoRepository;

  private final UserRepository userRepository;
  private final UserService userService;
  private final SaldoService saldoService;

  public DepositoController(DepositoRepository depositoRepository, GastoRepository gastoRepository, UserRepository userRepository, UserService userService, SaldoService saldoService) {
    this.depositoRepository = depositoRepository;
    this.gastoRepository = gastoRepository;
    this.userRepository=userRepository;
    this.userService = userService;
    this.saldoService = saldoService;
  }

  @GetMapping
  public ResponseEntity<List<DepositoDTO>> findAll(
    @RequestParam(required = false) String anoMes){
    if(anoMes==null){
      anoMes=Utils.getAnoMesAtual();
    }
    List<DepositoDTO> list=depositoRepository.findByUsuarioAndData(userService.getUsuarioLogado(),anoMes);
    return ResponseEntity.ok(list);
  }
  @PostMapping
  public ResponseEntity<DepositoDTO> save(@RequestBody DepositoDTO dto){
    Deposito deposito= DepositoBuilder.init()
      .withData(dto.getData())
      .withDescricao(dto.getDescricao())
      .withUsuario(userService.getUsuarioLogado())
      .withValor(dto.getValor())
      .withGastoVinculado(gastoRepository.findById(dto.getGastoVinculado()).orElse(null))
      .build();
    deposito=depositoRepository.save(deposito);
    saldoService.atualizarSaldo(deposito);
    return ResponseEntity.ok(new DepositoDTO(deposito));

  }
  @PutMapping(value = "/{id}")
  public ResponseEntity<DepositoDTO> save(@RequestBody DepositoDTO dto, @PathVariable long id){
    System.out.println("id");
    Deposito deposito=DepositoBuilder
      .init()
      .withDeposito(depositoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Recurso não encontrado")))
      .withData(dto.getData())
      .withDescricao(dto.getDescricao())
      .withValor(dto.getValor())
      .withGastoVinculado(gastoRepository.findById(dto.getGastoVinculado()).orElse(null))
      .build();
    saldoService.atualizarSaldo(deposito);

    return ResponseEntity.ok(new DepositoDTO(deposito));
  }
}

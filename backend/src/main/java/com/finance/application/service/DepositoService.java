package com.finance.application.service;

import com.finance.application.builder.DepositoBuilder;
import com.finance.application.dto.DepositoDTO;
import com.finance.application.model.Deposito;
import com.finance.application.repository.DepositoRepository;
import com.finance.application.repository.GastoRepository;
import com.finance.autentication.service.UserService;
import com.finance.configuration.Utils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositoService {

  private final DepositoRepository depositoRepository;
  private final UserService userService;
  private final SaldoService saldoService;
  private final GastoRepository gastoRepository;

  public DepositoService(DepositoRepository depositoRepository, UserService userService, SaldoService saldoService, GastoRepository gastoRepository) {
    this.depositoRepository = depositoRepository;
    this.userService = userService;
    this.saldoService = saldoService;
    this.gastoRepository = gastoRepository;
  }

  public List<DepositoDTO> findAll(String anoMes) {
    if(anoMes==null){
      anoMes= Utils.getAnoMesAtual();
    }
    List<DepositoDTO> list=depositoRepository.findByUsuarioAndData(userService.getUsuarioLogado(),anoMes);
    return list;
  }
  public Deposito save(DepositoDTO dto){
    Deposito deposito= DepositoBuilder.init()
      .withData(dto.getData())
      .withDescricao(dto.getDescricao())
      .withUsuario(userService.getUsuarioLogado())
      .withValor(dto.getValor())
      .withGastoVinculado(gastoRepository.findById(dto.getGastoVinculado()).orElse(null))
      .build();
    deposito=depositoRepository.save(deposito);
    saldoService.atualizarSaldo(deposito);
    return deposito;
  }
  public Deposito update(DepositoDTO dto, long id){
    Deposito deposito=DepositoBuilder
      .init()
      .withDeposito(depositoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Recurso não encontrado")))
      .withData(dto.getData())
      .withDescricao(dto.getDescricao())
      .withValor(dto.getValor())
      .withGastoVinculado(gastoRepository.findById(dto.getGastoVinculado()).orElse(null))
      .build();
    deposito=depositoRepository.save(deposito);
    saldoService.atualizarSaldo(deposito);
    return deposito;
  }
}

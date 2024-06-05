package com.finance.application.controller;

import com.finance.autentication.service.UserService;
import com.finance.configuration.enums.ESituacaoEncerramento;
import com.finance.configuration.enums.ETipoGasto;
import com.finance.application.model.Gasto;
import com.finance.application.repository.GastoRepository;
import com.finance.application.service.SaldoService;
import com.finance.configuration.Utils;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/encerramentoMensal")
public class EncerramentoMensalController {


  private final GastoRepository gastoRepository;
  private final SaldoService saldoService;
  private final UserService userService;

  public EncerramentoMensalController(GastoRepository gastoRepository, SaldoService saldoService, UserService userService) {
    this.gastoRepository = gastoRepository;
    this.saldoService = saldoService;
    this.userService = userService;
  }

  @GetMapping("/{competencia}")
  public Map findOne(@PathVariable String competencia){
    Map<String, ESituacaoEncerramento> result=new HashMap<>();
    List<Gasto> gastos=gastoRepository.findAllEncerramento(userService.getUsuarioLogado(), competencia, ETipoGasto.FIXO);
    if(gastos.isEmpty()){
      result.put("fixo", ESituacaoEncerramento.SEM_VALORES);
    } else if (gastos.stream().allMatch(gasto->gasto.isEncerrado())) {
      result.put("fixo", ESituacaoEncerramento.ENCERRADO);
    }else{
      result.put("fixo", ESituacaoEncerramento.NAO_ENCERRADO);
    }

    List<Gasto> gastoVariavel=gastoRepository.findAllEncerramento(userService.getUsuarioLogado(), competencia, ETipoGasto.VARIAVEL);
    if(gastoVariavel.isEmpty()){
      result.put("variavel", ESituacaoEncerramento.SEM_VALORES);
    } else if (gastoVariavel.stream().allMatch(gasto->gasto.isEncerrado())) {
      result.put("variavel", ESituacaoEncerramento.ENCERRADO);
    }else{
      result.put("variavel", ESituacaoEncerramento.NAO_ENCERRADO);
    }

    return result;
  }
  @PutMapping("/{competencia}/{tipo}")
  public Map encerrarMes(@PathVariable String competencia,@PathVariable String tipo){
    ETipoGasto tipoGasto=ETipoGasto.valueOf(tipo);
    List<Gasto> gastos=null;
    if(tipoGasto==ETipoGasto.FIXO){
      gastos=gastoRepository.findAllGastoFixo(userService.getUsuarioLogado(), competencia);
    }else{
      gastos=gastoRepository.findAllGastoVariavelCartao(userService.getUsuarioLogado(), competencia);
    }
    for(Gasto gasto: gastos){
      gasto.setEncerrado(true);
      gastoRepository.save(gasto);
      saldoService.atualizarSaldo(gasto);
    }
    return this.findOne(competencia);

  }
  @GetMapping("/saldo")
  public Double getSaldoAtual(){
    return userService.getUsuarioLogado().getContaCorrente();
  }


}

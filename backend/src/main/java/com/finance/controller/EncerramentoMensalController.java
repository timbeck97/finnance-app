package com.finance.controller;

import com.finance.enums.ESituacaoEncerramento;
import com.finance.enums.ETipoGasto;
import com.finance.model.Gasto;
import com.finance.repository.GastoRepository;
import com.finance.service.SaldoService;
import com.finance.service.Utils;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/encerramentoMensal")
public class EncerramentoMensalController {


  private final GastoRepository gastoRepository;
  private final Utils utils;
  private final SaldoService saldoService;


  public EncerramentoMensalController( GastoRepository gastoRepository, Utils utils, SaldoService saldoService) {
    this.gastoRepository = gastoRepository;
    this.utils = utils;
    this.saldoService = saldoService;
  }

  @GetMapping("/{competencia}")
  public Map findOne(@PathVariable String competencia){
    Map<String, ESituacaoEncerramento> result=new HashMap<>();
    List<Gasto> gastos=gastoRepository.findAllEncerramento(utils.getUsuarioLogado(), competencia, ETipoGasto.FIXO);
    if(gastos.isEmpty()){
      result.put("fixo", ESituacaoEncerramento.SEM_VALORES);
    } else if (gastos.stream().allMatch(gasto->gasto.isEncerrado())) {
      result.put("fixo", ESituacaoEncerramento.ENCERRADO);
    }else{
      result.put("fixo", ESituacaoEncerramento.NAO_ENCERRADO);
    }

    List<Gasto> gastoVariavel=gastoRepository.findAllEncerramento(utils.getUsuarioLogado(), competencia, ETipoGasto.VARIAVEL);
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
      gastos=gastoRepository.findAllGastoFixo(utils.getUsuarioLogado(), competencia);
    }else{
      gastos=gastoRepository.findAllGastoVariavelCartao(utils.getUsuarioLogado(), competencia);
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
    return utils.getUsuarioLogado().getContaCorrente();
  }


}

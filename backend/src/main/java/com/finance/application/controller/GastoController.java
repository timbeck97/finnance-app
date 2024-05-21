package com.finance.application.controller;

import com.finance.application.builder.GastoBuilder;
import com.finance.application.dto.GastoDTO;
import com.finance.configuration.enums.EFormaPagamento;
import com.finance.configuration.enums.ETipoGasto;
import com.finance.application.model.Gasto;
import com.finance.autentication.model.User;
import com.finance.application.repository.GastoRepository;
import com.finance.application.service.SaldoService;
import com.finance.configuration.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/gastos")
public class GastoController {

  private final Utils util;
  private final GastoRepository gastoRepository;

  private final SaldoService saldoService;

  public GastoController(GastoRepository gastoRepository, Utils util, SaldoService saldoService) {
    this.gastoRepository = gastoRepository;
    this.util = util;
    this.saldoService = saldoService;
  }

  @GetMapping
  public ResponseEntity<List<GastoDTO>> findAll(
    @RequestParam(required = false) String anoMes,
    @RequestParam(required = false, defaultValue = "1")int pageNumber,
    @RequestParam(required = false, defaultValue = "5")int pageSize,
    @RequestParam(required = false, defaultValue = "VARIAVEL") ETipoGasto tipoGasto,
    @RequestParam(required = false) String filtro,
    HttpServletResponse resp){
    if(anoMes==null){
      anoMes=util.getAnoMesAtual();
    }
    Pageable page=PageRequest.of(pageNumber-1, pageSize);
    Page<GastoDTO> gastos=gastoRepository.findByData(anoMes,filtro,util.getUsuarioLogado(),tipoGasto, page);
    resp.setHeader("X-Total-Count",String.valueOf(gastos.getTotalElements()));
    return ResponseEntity.ok(gastos.getContent());

  }
  @GetMapping(value = "/mensal")
  public ResponseEntity<Map<String, Double>> findAllMensal(@RequestParam(required = false) String anoMes){
    if(anoMes==null){
      anoMes=util.getAnoMesAtual();
    }
    User usuarioLogado = util.getUsuarioLogado();
    List result=gastoRepository.findGastosMensais(anoMes, usuarioLogado.getId());
    Map<String, Double> values=new HashMap<>();
    for(Object o: result){
      Object[] columns=(Object[])o;
      if(columns[0]==null)break;
      values.put("totalPix", ((BigDecimal)columns[0]).doubleValue());
      values.put("totalCartao", ((BigDecimal)columns[1]).doubleValue());
    }

    return ResponseEntity.ok(values);

  }
  @PostMapping
  public ResponseEntity<GastoDTO> save(@RequestBody GastoDTO dto){
    Gasto gasto=new Gasto();
    gasto.setCategoria(dto.getCategoria());
    gasto.setData(dto.getData());
    gasto.setDescricao(dto.getDescricao());
    gasto.setUsuario(util.getUsuarioLogado());
    if(dto.getFormaPagamento().equals(EFormaPagamento.PIX)){
      gasto.setEncerrado(true);
    }
    gasto.setFormaPagamento(dto.getFormaPagamento());
    gasto.setValor(dto.getValor());
    gasto.setTipoGasto(dto.getTipoGasto());
    gasto=gastoRepository.save(gasto);
    if(gasto.getFormaPagamento().equals(EFormaPagamento.PIX)){
      saldoService.atualizarSaldo(gasto);
    }
    return ResponseEntity.status(201).body(new GastoDTO(gasto));

  }
  @PostMapping(value = "/fixos/copiar")
  public ResponseEntity<Void> copy(){
    User user=util.getUsuarioLogado();
    String mesAnterior=util.getAnoMesAnterior();
    List<Gasto> gastos=gastoRepository.findByData(mesAnterior, user, ETipoGasto.FIXO);
    for(Gasto dto:gastos){
      List<Gasto> existentes=gastoRepository.findByDescricao(dto.getDescricao());
      if(existentes.size()>0) continue;
      Gasto gasto= GastoBuilder.
        create().
        withCategoria(dto.getCategoria()).
        withData(util.getAnoMesAtual()).
        withDescricao(dto.getDescricao()).
        withUsuario(util.getUsuarioLogado()).
        withFormaPagamento(dto.getFormaPagamento()).
        withValor(dto.getValor()).
        withTipoGasto(dto.getTipoGasto()).
        build();
      gastoRepository.save(gasto);
    }
    return ResponseEntity.status(201).build();

  }
  @PutMapping(value = "/{id}")
  public ResponseEntity<GastoDTO> save(@RequestBody GastoDTO dto, @PathVariable long id){
    Gasto gasto=gastoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Recurso não encontrado"));
    gasto=GastoBuilder.create(gasto).
      withCategoria(dto.getCategoria()).
      withData(dto.getData()).
      withDescricao(dto.getDescricao()).
      withUsuario(util.getUsuarioLogado()).
      withFormaPagamento(dto.getFormaPagamento()).
      withValor(dto.getValor()).
      withTipoGasto(dto.getTipoGasto()).
      withEncerrado(dto.getFormaPagamento().equals(EFormaPagamento.PIX)).
      build();

    gasto=gastoRepository.save(gasto);
    if(gasto.getFormaPagamento().equals(EFormaPagamento.PIX)){ //gasto anterior
      if(dto.getFormaPagamento().equals(EFormaPagamento.CARTAO)){ //gasto novo atualizado
        saldoService.reverterGasto(gasto);
      }else{
        saldoService.atualizarSaldo(gasto);
      }
    }else{
      if(dto.getFormaPagamento().equals(EFormaPagamento.PIX)){
        saldoService.atualizarSaldo(gasto);
      }
    }
    return ResponseEntity.ok(new GastoDTO(gasto));
  }
  @GetMapping(value = "/autocomplete/{competencia}")
  public ResponseEntity<List<GastoDTO>> findAllAutoComplete(
    @PathVariable String competencia,
    @RequestParam(required = false) String filtro,
    @RequestParam(required = false) Long id,
    HttpServletResponse resp){
    List<GastoDTO> gastos=null;
    if(id!=null){
      gastos= Arrays.asList(new GastoDTO(gastoRepository.findById(id).get()));
    }else{
      Pageable page=PageRequest.of(0, 5);
      gastos=gastoRepository.findByData(competencia,filtro,util.getUsuarioLogado(),ETipoGasto.VARIAVEL, page).getContent();
    }
    return ResponseEntity.ok(gastos);

  }
}

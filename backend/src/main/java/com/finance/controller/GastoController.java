package com.finance.controller;

import com.finance.dto.GastoDTO;
import com.finance.enums.ETipoGasto;
import com.finance.model.Gasto;
import com.finance.model.User;
import com.finance.repository.GastoRepository;
import com.finance.service.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/gastos")
public class GastoController {

  private final Utils util;
  private final GastoRepository gastoRepository;

  public GastoController(GastoRepository gastoRepository, Utils util) {
    this.gastoRepository = gastoRepository;
    this.util = util;
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
    gasto.setFormaPagamento(dto.getFormaPagamento());
    gasto.setValor(dto.getValor());
    gasto.setTipoGasto(dto.getTipoGasto());
    return ResponseEntity.status(201).body(new GastoDTO(gastoRepository.save(gasto)));

  }
  @PostMapping(value = "/fixos/copiar")
  public ResponseEntity<Void> copy(){
    User user=util.getUsuarioLogado();
    String mesAnterior=util.getAnoMesAnterior();
    List<Gasto> gastos=gastoRepository.findByData(mesAnterior, user, ETipoGasto.FIXO);
    for(Gasto dto:gastos){
      List<Gasto> existentes=gastoRepository.findByDescricao(dto.getDescricao());
      if(existentes.size()>0) continue;

      Gasto gasto=new Gasto();
      gasto.setCategoria(dto.getCategoria());
      gasto.setData(util.getAnoMesAtual());
      gasto.setDescricao(dto.getDescricao());
      gasto.setUsuario(util.getUsuarioLogado());
      gasto.setFormaPagamento(dto.getFormaPagamento());
      gasto.setValor(dto.getValor());
      gasto.setTipoGasto(dto.getTipoGasto());
      gastoRepository.save(gasto);
    }
    return ResponseEntity.status(201).build();

  }
  @PutMapping(value = "/{id}")
  public ResponseEntity<GastoDTO> save(@RequestBody GastoDTO dto, @PathVariable long id){
    Gasto gasto=gastoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Recurso não encontrado"));
    gasto.setCategoria(dto.getCategoria());
    gasto.setData(dto.getData());
    gasto.setDescricao(dto.getDescricao());
    gasto.setUsuario(util.getUsuarioLogado());
    gasto.setFormaPagamento(dto.getFormaPagamento());
    gasto.setValor(dto.getValor());
    gasto.setTipoGasto(dto.getTipoGasto());
    return ResponseEntity.ok(new GastoDTO(gastoRepository.save(gasto)));
  }
  @GetMapping(value = "/autocomplete")
  public ResponseEntity<List<GastoDTO>> findAllAutoComplete(
    @RequestParam(required = false) String filtro,
    @RequestParam(required = false) Long id,
    HttpServletResponse resp){
    List<GastoDTO> gastos=null;
    if(id!=null){
      gastos= Arrays.asList(new GastoDTO(gastoRepository.findById(id).get()));
    }else{
      String anoMes=util.getAnoMesAtual();
      Pageable page=PageRequest.of(0, 5);
      gastos=gastoRepository.findByData(anoMes,filtro,util.getUsuarioLogado(),ETipoGasto.VARIAVEL, page).getContent();
    }
    return ResponseEntity.ok(gastos);

  }
}

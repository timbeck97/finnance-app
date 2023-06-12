package com.finance.controller;

import com.finance.dto.GastoDTO;
import com.finance.model.Gasto;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/gastos")
public class GastoController {

  @Autowired
  private Utils util;

  @Autowired
  private GastoRepository gastoRepository;
  @GetMapping
  public ResponseEntity<List<GastoDTO>> findAll(
    @RequestParam(required = false) String anoMes,
    @RequestParam(required = false, defaultValue = "1")int pageNumber,
    @RequestParam(required = false, defaultValue = "5")int pageSize,
    HttpServletResponse resp){
    if(anoMes==null){
      anoMes=util.getAnoMesAtual();
    }
    Pageable page=PageRequest.of(pageNumber-1, pageSize);
    Page<GastoDTO> gastos=gastoRepository.findByData(anoMes,util.getUsuarioLogado(),page);
    resp.setHeader("X-Total-Count",String.valueOf(gastos.getTotalElements()));
    return ResponseEntity.ok(gastos.getContent());

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
    return ResponseEntity.ok(new GastoDTO(gastoRepository.save(gasto)));

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
    return ResponseEntity.ok(new GastoDTO(gastoRepository.save(gasto)));
  }
}

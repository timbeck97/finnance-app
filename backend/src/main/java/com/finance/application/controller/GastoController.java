package com.finance.application.controller;

import com.finance.application.dto.GastoDTO;
import com.finance.application.service.GastoService;
import com.finance.configuration.Utils;
import com.finance.configuration.enums.ETipoGasto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/gastos")
public class GastoController {


  private final GastoService gastoService;

  public GastoController(GastoService gastoService) {
    this.gastoService = gastoService;
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
      anoMes=Utils.getAnoMesAtual();
    }
    return ResponseEntity.ok(gastoService.findAll(anoMes, pageNumber, pageSize, tipoGasto, filtro, resp));
  }
  @GetMapping(value = "/mensal")
  public ResponseEntity<Map<String, Double>> findAllMensal(@RequestParam(required = false) String anoMes){
    return ResponseEntity.ok(gastoService.findAllMensal(anoMes));
  }
  @PostMapping
  public ResponseEntity<GastoDTO> save(@RequestBody GastoDTO dto){
    return ResponseEntity.status(201).body(new GastoDTO(gastoService.save(dto)));
  }
  @PostMapping(value = "/fixos/copiar")
  public ResponseEntity<Void> copy(){
    gastoService.copiarGastosFixos();
    return ResponseEntity.status(200).build();

  }
  @PutMapping(value = "/{id}")
  public ResponseEntity<GastoDTO> updateGasto(@RequestBody GastoDTO dto, @PathVariable long id){

    return ResponseEntity.ok(new GastoDTO(gastoService.updateGasto(dto, id)));
  }
  @GetMapping(value = "/autocomplete")
  public ResponseEntity<List<GastoDTO>> findAllAutoComplete(
    @RequestParam(required = false) String competencia,
    @RequestParam(required = false) String filtro,
    @RequestParam(required = false) Long id,
    HttpServletResponse resp){

    return ResponseEntity.ok(gastoService.findAllAutoComplete(competencia, filtro, id));

  }
}

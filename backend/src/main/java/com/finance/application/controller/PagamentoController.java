package com.finance.application.controller;

import com.finance.application.builder.PagamentoBuilder;
import com.finance.application.dto.PagamentoDTO;
import com.finance.application.model.Pagamento;
import com.finance.application.repository.PagamentoRepository;
import com.finance.autentication.model.User;
import com.finance.autentication.service.UserService;
import com.finance.configuration.Utils;
import com.finance.configuration.exceptions.DataNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {


  private final PagamentoRepository pagamentoRepository;
  private final UserService userService;

  public PagamentoController(PagamentoRepository pagamentoRepository, UserService userService) {
    this.pagamentoRepository = pagamentoRepository;
    this.userService = userService;
  }

  @GetMapping(value = "/{data}")
  public ResponseEntity<PagamentoDTO> findPgamento(@PathVariable String data){
    return ResponseEntity.ok().body(pagamentoRepository.findByDataAndUsuario(data, userService.getUsuarioLogado()));
  }
  @PostMapping
  public ResponseEntity<PagamentoDTO> savePagamento(@RequestBody PagamentoDTO pagamentoDTO){
    Pagamento novo= PagamentoBuilder.create()
      .withData(pagamentoDTO.getData())
      .withValor(pagamentoDTO.getValor())
      .withUsuario(userService.getUsuarioLogado())
      .build();
    return ResponseEntity.status(200).body(new PagamentoDTO(pagamentoRepository.save(novo)));
  }
  @PutMapping(value = "/{id}")
  public ResponseEntity<PagamentoDTO> updatePagamento(@PathVariable Long id,@RequestBody PagamentoDTO pagamentoDTO){
    Pagamento existente=pagamentoRepository.findById(id).orElseThrow(()->new DataNotFoundException("Pagamento não encontrado"));
    User usuarioLogado = userService.getUsuarioLogado();
    if(!existente.getUsuario().getEmail().equals(usuarioLogado.getEmail())){
      throw new IllegalArgumentException("Pagamento não pertence ao usuário logado");
    }
    Pagamento novo= PagamentoBuilder.create(existente)
      .withData(pagamentoDTO.getData())
      .withValor(pagamentoDTO.getValor())
      .withUsuario(userService.getUsuarioLogado())
      .build();
    return ResponseEntity.ok().body(new PagamentoDTO(pagamentoRepository.save(novo)));
  }

}

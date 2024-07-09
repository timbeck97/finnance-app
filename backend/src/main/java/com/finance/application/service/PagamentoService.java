package com.finance.application.service;

import com.finance.application.builder.PagamentoBuilder;
import com.finance.application.dto.PagamentoDTO;
import com.finance.application.model.Pagamento;
import com.finance.application.repository.PagamentoRepository;
import com.finance.autentication.model.User;
import com.finance.autentication.service.UserService;
import com.finance.configuration.exceptions.DataNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

  private final PagamentoRepository pagamentoRepository;
  private final UserService userService;

  public PagamentoService(PagamentoRepository pagamentoRepository, UserService userService) {
    this.pagamentoRepository = pagamentoRepository;
    this.userService = userService;
  }

  public PagamentoDTO findByData(String data) {
    return pagamentoRepository.findByDataAndUsuario(data, userService.getUsuarioLogado());
  }
  public Pagamento save(PagamentoDTO pagamentoDTO){
    Pagamento novo= PagamentoBuilder.create()
      .withData(pagamentoDTO.getData())
      .withValor(pagamentoDTO.getValor())
      .withUsuario(userService.getUsuarioLogado())
      .build();
    return pagamentoRepository.save(novo);
  }
  public Pagamento updatePagamento(PagamentoDTO pagamentoDTO, Long id){
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
    return pagamentoRepository.save(novo);
  }
}

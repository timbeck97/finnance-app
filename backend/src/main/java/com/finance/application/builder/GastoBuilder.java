package com.finance.application.builder;

import com.finance.application.dto.GastoDTO;
import com.finance.application.model.Gasto;
import com.finance.autentication.dto.UserDTO;
import com.finance.autentication.model.User;
import com.finance.configuration.enums.ECategoriaGasto;
import com.finance.configuration.enums.EFormaPagamento;
import com.finance.configuration.enums.ETipoGasto;

public class GastoBuilder {
  Gasto gasto;


  public static GastoBuilder create() {
    GastoBuilder builder = new GastoBuilder();
    builder.gasto = new Gasto();
    return builder;
  }
  public static GastoBuilder create(Gasto gasto) {
    GastoBuilder builder = new GastoBuilder();
    builder.gasto = gasto;
    return builder;
  }
  public GastoBuilder withId(Long id) {
    gasto.setId(id);
    return this;
  }
  public GastoBuilder withDescricao(String descricao) {
    gasto.setDescricao(descricao);
    return this;
  }
  public GastoBuilder withData(String data) {
    gasto.setData(data);
    return this;
  }
  public GastoBuilder withCategoria(ECategoriaGasto categoria) {
    gasto.setCategoria(categoria);
    return this;
  }
  public GastoBuilder withValor(Double valor) {
    gasto.setValor(valor);
    return this;
  }
  public GastoBuilder withTipoGasto(ETipoGasto tipoGasto) {
    gasto.setTipoGasto(tipoGasto);
    return this;
  }
  public GastoBuilder withUsuario(User usuario) {
    gasto.setUsuario(usuario);
    return this;
  }
  public GastoBuilder withFormaPagamento(EFormaPagamento formaPagamento) {
    gasto.setFormaPagamento(formaPagamento);
    return this;
  }
  public GastoBuilder withEncerrado(boolean encerrado) {
    gasto.setEncerrado(encerrado);
    return this;
  }
  public GastoBuilder withDto(GastoDTO dto, User user){
    gasto.setCategoria(dto.getCategoria());
    gasto.setData(dto.getData());
    gasto.setDescricao(dto.getDescricao());
    gasto.setUsuario(user);
    gasto.setFormaPagamento(dto.getFormaPagamento());
    gasto.setValor(dto.getValor());
    gasto.setTipoGasto(dto.getTipoGasto());
    return this;
  }
  public Gasto build() {
    return gasto;
  }

}

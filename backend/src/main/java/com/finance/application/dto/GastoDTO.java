package com.finance.application.dto;

import com.finance.application.model.Gasto;
import com.finance.autentication.dto.UserDTO;
import com.finance.configuration.enums.ECategoriaGasto;
import com.finance.configuration.enums.EFormaPagamento;
import com.finance.configuration.enums.ETipoGasto;
import com.finance.configuration.Utils;

public class GastoDTO {
  private Long id;

  private String descricao;

  private ECategoriaGasto categoria;

  private EFormaPagamento formaPagamento;

  private ETipoGasto tipoGasto;

  private double valor;

  private String data;

  private UserDTO user;

  public GastoDTO() {
  }

  public GastoDTO(Long id, String descricao, ECategoriaGasto categoria, EFormaPagamento formaPagamento, double valor, String data, UserDTO user) {
    this.id = id;
    this.descricao = descricao;
    this.categoria = categoria;
    this.formaPagamento = formaPagamento;
    this.valor = valor;
    this.data = data;
    this.user = user;
  }
  public GastoDTO(Gasto g) {
    this.id = g.getId();
    this.descricao = g.getDescricao();
    this.categoria = g.getCategoria();
    this.formaPagamento = g.getFormaPagamento();
    this.valor = g.getValor();
    this.data = g.getData();
    this.user=new UserDTO(g.getUsuario());
    this.tipoGasto=g.getTipoGasto();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public ECategoriaGasto getCategoria() {
    return categoria;
  }

  public void setCategoria(ECategoriaGasto categoria) {
    this.categoria = categoria;
  }

  public EFormaPagamento getFormaPagamento() {
    return formaPagamento;
  }

  public void setFormaPagamento(EFormaPagamento formaPagamento) {
    this.formaPagamento = formaPagamento;
  }

  public double getValor() {
    return valor;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }

  public ETipoGasto getTipoGasto() {
    return tipoGasto;
  }

  public void setTipoGasto(ETipoGasto tipoGasto) {
    this.tipoGasto = tipoGasto;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getDescricaoAutoComplete() {
    return Utils.formatDoubleToBRCurrency(valor)+" - "+descricao;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }
}

package com.finance.dto;

import com.finance.enums.ECategoriaGasto;
import com.finance.enums.EFormaPagamento;
import com.finance.enums.ETipoGasto;
import com.finance.model.Gasto;
import com.finance.model.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

public class GastoDTO {
  private Long id;

  private String descricao;

  private ECategoriaGasto categoria;

  private EFormaPagamento formaPagamento;

  private ETipoGasto tipoGasto;

  private double valor;

  private String data;

  private String userName;

  public GastoDTO() {
  }

  public GastoDTO(Long id, String descricao, ECategoriaGasto categoria, EFormaPagamento formaPagamento, double valor, String data, String usuario) {
    this.id = id;
    this.descricao = descricao;
    this.categoria = categoria;
    this.formaPagamento = formaPagamento;
    this.valor = valor;
    this.data = data;
    this.userName = usuario;
  }
  public GastoDTO(Gasto g) {
    this.id = g.getId();
    this.descricao = g.getDescricao();
    this.categoria = g.getCategoria();
    this.formaPagamento = g.getFormaPagamento();
    this.valor = g.getValor();
    this.data = g.getData();
    this.userName = g.getUsuario().getName();
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
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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
}

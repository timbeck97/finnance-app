package com.finance.dto;

import com.finance.model.Deposito;

import java.time.LocalDate;

public class DepositoDTO {
  private long id;
  private String descricao;
  private double valor;
  private String data;

  private long gastoVinculado;

  public DepositoDTO() {
  }

  public DepositoDTO(Deposito d) {
    this.id = d.getId();
    this.descricao = d.getDescricao();
    this.valor = d.getValor();
    this.data = d.getData();
    if(d.getGastoVinculado()!=null){
      this.gastoVinculado=d.getGastoVinculado().getId();
    }
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public double getValor() {
    return valor;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public long getGastoVinculado() {
    return gastoVinculado;
  }

  public void setGastoVinculado(long gastoVinculado) {
    this.gastoVinculado = gastoVinculado;
  }
}

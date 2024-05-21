package com.finance.application.model;

import com.finance.autentication.model.User;

import javax.persistence.*;

@Entity
public class Deposito {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deposito_sequence")
  @SequenceGenerator(name = "deposito_sequence", sequenceName = "deposito_seq")
  private Long id;

  @Column(columnDefinition = "varchar(100) not null")
  private String descricao;

  @Column(columnDefinition = "numeric(14,2) not null")
  private double valor;

  @Column(columnDefinition = "varchar(6)")
  private String data;

  @ManyToOne
  private User usuario;

  @ManyToOne(optional = true)
  private Gasto gastoVinculado;

  public Deposito() {

  }

  public Deposito(Long id, String descricao, double valor, String data, User usuario, Gasto gastoVinculado) {
    this.id = id;
    this.descricao = descricao;
    this.valor = valor;
    this.data = data;
    this.usuario = usuario;
    this.gastoVinculado = gastoVinculado;
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

  public User getUsuario() {
    return usuario;
  }

  public void setUsuario(User usuario) {
    this.usuario = usuario;
  }

  public Gasto getGastoVinculado() {
    return gastoVinculado;
  }

  public void setGastoVinculado(Gasto gastoVinculado) {
    this.gastoVinculado = gastoVinculado;
  }
}

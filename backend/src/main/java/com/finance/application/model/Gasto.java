package com.finance.application.model;

import com.finance.autentication.model.User;
import com.finance.configuration.enums.ECategoriaGasto;
import com.finance.configuration.enums.EFormaPagamento;
import com.finance.configuration.enums.ETipoGasto;

import javax.persistence.*;

@Entity
public class Gasto {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="gasto_sequence")
    @SequenceGenerator(name="gasto_sequence", sequenceName="gasto_seq")
    private Long id;

    @Column(columnDefinition = "varchar(100) not null")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50) not null")
    private ECategoriaGasto categoria;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(30) not null")
    private EFormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(30) not null default 'VARIAVEL'")
    private ETipoGasto tipoGasto;

    @Column(columnDefinition = "numeric(14,2) not null")
    private double valor;

    @Column(columnDefinition = "varchar(6)")
    private String data;

    @ManyToOne
    private User usuario;

    @Column(columnDefinition = "boolean default false")
    private boolean encerrado;

  public Gasto() {

  }

  public Gasto(Long id, String descricao, ECategoriaGasto categoria, EFormaPagamento formaPagamento, double valor, String data, User usuario, ETipoGasto tipoGasto) {
    this.id = id;
    this.descricao = descricao;
    this.categoria = categoria;
    this.formaPagamento = formaPagamento;
    this.valor = valor;
    this.data = data;
    this.usuario = usuario;
    this.tipoGasto=tipoGasto;
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

  public User getUsuario() {
    return usuario;
  }

  public void setUsuario(User usuario) {
    this.usuario = usuario;
  }

  public ETipoGasto getTipoGasto() {
    return tipoGasto;
  }

  public void setTipoGasto(ETipoGasto tipoGasto) {
    this.tipoGasto = tipoGasto;
  }

  public boolean isEncerrado() {
    return encerrado;
  }

  public void setEncerrado(boolean encerrado) {
    this.encerrado = encerrado;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}

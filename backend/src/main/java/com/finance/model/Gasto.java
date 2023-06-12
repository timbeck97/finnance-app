package com.finance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finance.enums.ECategoriaGasto;
import com.finance.enums.EFormaPagamento;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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

    @Column(columnDefinition = "numeric(14,2) not null")
    private double valor;

    @Column(columnDefinition = "date default current_date")
    private LocalDate data;

    @ManyToOne
    private User usuario;

  public Gasto() {

  }

  public Gasto(Long id, String descricao, ECategoriaGasto categoria, EFormaPagamento formaPagamento, double valor, LocalDate data, User usuario) {
    this.id = id;
    this.descricao = descricao;
    this.categoria = categoria;
    this.formaPagamento = formaPagamento;
    this.valor = valor;
    this.data = data;
    this.usuario = usuario;
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

  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }
}

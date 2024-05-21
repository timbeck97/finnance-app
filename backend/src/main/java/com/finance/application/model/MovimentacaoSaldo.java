package com.finance.application.model;

import com.finance.autentication.model.User;
import com.finance.configuration.enums.ETipoMovimentacao;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class MovimentacaoSaldo {

  @Id
  @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="movimento_saldo_sequence")
  @SequenceGenerator(name="movimento_saldo_sequence", sequenceName="movimento_saldo_seq")
  private long id;
  @ManyToOne
  private User user;
  @Column(columnDefinition = "numeric(14,2) not null")
  private double valor;
  @Column(columnDefinition = "numeric(14,2) not null")
  private double saldoAnterior;
  @Column(columnDefinition = "numeric(14,2) not null")
  private double saldoAtualizado;

  @Column(columnDefinition = "date default current_date")
  private LocalDate data;
  @Column(columnDefinition = "varchar(200) not null")
  private String descricao;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "varchar(30) not null")
  private ETipoMovimentacao tipoMovimentacao;

  @ManyToOne(optional = true)
  private Gasto gasto;

  @ManyToOne(optional = true)
  private Deposito deposito;

  public MovimentacaoSaldo() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public double getValor() {
    return valor;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }

  public double getSaldoAnterior() {
    return saldoAnterior;
  }

  public void setSaldoAnterior(double saldoAnterior) {
    this.saldoAnterior = saldoAnterior;
  }

  public double getSaldoAtualizado() {
    return saldoAtualizado;
  }

  public void setSaldoAtualizado(double saldoAtualizado) {
    this.saldoAtualizado = saldoAtualizado;
  }

  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public ETipoMovimentacao getTipoMovimentacao() {
    return tipoMovimentacao;
  }

  public void setTipoMovimentacao(ETipoMovimentacao tipoMovimentacao) {
    this.tipoMovimentacao = tipoMovimentacao;
  }

  public Gasto getGasto() {
    return gasto;
  }

  public void setGasto(Gasto gasto) {
    this.gasto = gasto;
  }

  public Deposito getDeposito() {
    return deposito;
  }

  public void setDeposito(Deposito deposito) {
    this.deposito = deposito;
  }
}

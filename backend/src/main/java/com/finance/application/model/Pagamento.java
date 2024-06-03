package com.finance.application.model;

import com.finance.autentication.model.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Pagamento implements Serializable {
  @Id
  @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="pagamento_sequence")
  @SequenceGenerator(name="pagamento_sequence", sequenceName="pagamento_seq")
  private long id;
  @ManyToOne
  private User usuario;
  @Column(columnDefinition = "numeric(14,2) not null")
  private double valor;
  @Column(columnDefinition = "varchar(6)")
  private String data;

  public Pagamento() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUsuario() {
    return usuario;
  }

  public void setUsuario(User usuario) {
    this.usuario = usuario;
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
}

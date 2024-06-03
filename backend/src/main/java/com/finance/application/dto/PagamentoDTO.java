package com.finance.application.dto;

import com.finance.application.model.Pagamento;
import com.finance.autentication.dto.UserDTO;

public class PagamentoDTO {
    private long id;
    private double valor;
    private String data;

    public PagamentoDTO() {
    }

  public PagamentoDTO(Pagamento p) {
    this.id = p.getId();
    this.valor = p.getValor();
    this.data = p.getData();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

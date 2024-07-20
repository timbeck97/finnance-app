package com.finance.application.dto;

import com.finance.application.model.Pagamento;
import com.finance.autentication.dto.UserDTO;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class PagamentoDTO {
    private long id;

    @DecimalMin(value = "0.1", message = "O valor do pagamento deve ser maior que zero")
    private double valor;
    @NotBlank(message = "A data do pagamento é obrigatória")
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

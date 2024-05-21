package com.finance.application.dto;

public class EncerramentoDTO {
  private boolean gastosFixos;
  private boolean gastosVariaveis;

  public boolean isGastosFixos() {
    return gastosFixos;
  }

  public void setGastosFixos(boolean gastosFixos) {
    this.gastosFixos = gastosFixos;
  }

  public boolean isGastosVariaveis() {
    return gastosVariaveis;
  }

  public void setGastosVariaveis(boolean gastosVariaveis) {
    this.gastosVariaveis = gastosVariaveis;
  }
}

package com.finance.application.dto;

public class PanelValorTotal {
  private double valor;
  private boolean positivo;
  private double percentual;

  public PanelValorTotal(double valor, boolean positivo, double percentual) {
    this.valor = valor;
    this.positivo = positivo;
    if(percentual>0) {
      this.percentual = (percentual-1)*100;
    }
  }

  public double getValor() {
    return valor;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }

  public boolean isPositivo() {
    return positivo;
  }

  public void setPositivo(boolean positivo) {
    this.positivo = positivo;
  }

  public double getPercentual() {
    return percentual;
  }

  public void setPercentual(double percentual) {
    this.percentual = percentual;
  }
}

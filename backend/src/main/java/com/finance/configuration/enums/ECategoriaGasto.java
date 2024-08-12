package com.finance.configuration.enums;

public enum ECategoriaGasto {

  LASER("Laser"),
  GASOLINA("Gasolina"),
  OUTROS("Outros"),
  SAUDE("Saúde"),
  CARRO("Carro");

  String descricao;

  ECategoriaGasto(String desc){
    this.descricao=desc;
  }

  public String getDescricao() {
    return descricao;
  }
}

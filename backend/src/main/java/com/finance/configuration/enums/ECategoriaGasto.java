package com.finance.configuration.enums;

public enum ECategoriaGasto {

  LASER("Laser"),
  GASOLINA("GASOLINA"),
  OUTROS("Outros"),
  SAUDE("Saúde"),
  CARRO("Carro");

  String descricao;

  ECategoriaGasto(String desc){
    this.descricao=desc;
  }
}

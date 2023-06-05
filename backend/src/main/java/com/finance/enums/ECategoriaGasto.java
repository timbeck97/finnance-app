package com.finance.enums;

public enum ECategoriaGasto {

  LASER("Laser"),
  GASOLINA("GASOLINA"),
  OUTROS("Outros"),
  SAUDE("Saúde");

  String descricao;

  ECategoriaGasto(String desc){
    this.descricao=desc;
  }
}

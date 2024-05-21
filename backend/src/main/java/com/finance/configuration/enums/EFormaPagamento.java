package com.finance.configuration.enums;

public enum EFormaPagamento {
  PIX("Pix"),
  CARTAO("Cartão");

  String descricao;

  EFormaPagamento(String desc){
    this.descricao=desc;
  }
}

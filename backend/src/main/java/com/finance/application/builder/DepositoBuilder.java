package com.finance.application.builder;

import com.finance.application.model.Deposito;
import com.finance.application.model.Gasto;
import com.finance.autentication.model.User;

public class DepositoBuilder {
  Deposito deposito;

  public DepositoBuilder(){
    deposito = new Deposito();
  }
  public static DepositoBuilder init(){
    return new DepositoBuilder();
  }
  public DepositoBuilder withData(String data){
    deposito.setData(data);
    return this;
  }
  public DepositoBuilder withDescricao(String descricao){
    deposito.setDescricao(descricao);
    return this;
  }
  public DepositoBuilder withUsuario(User usuario){
    deposito.setUsuario(usuario);
    return this;
  }
  public DepositoBuilder withValor(double valor){
    deposito.setValor(valor);
    return this;
  }
  public DepositoBuilder withGastoVinculado(Gasto gastoVinculado){
    deposito.setGastoVinculado(gastoVinculado);
    return this;
  }
  public DepositoBuilder withDeposito(Deposito deposito){
    this.deposito=deposito;
    return this;
  }
  public Deposito build(){
    return deposito;
  }
}

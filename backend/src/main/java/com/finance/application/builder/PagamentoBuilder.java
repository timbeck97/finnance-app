package com.finance.application.builder;

import com.finance.application.model.Pagamento;
import com.finance.autentication.model.User;

public class PagamentoBuilder {
  Pagamento pagamento;


  public PagamentoBuilder(){
    this.pagamento = new Pagamento();
  }
  public PagamentoBuilder(Pagamento p){
    this.pagamento = p;
  }public static PagamentoBuilder create(Pagamento p){
    return new PagamentoBuilder(p);
  }
  public static PagamentoBuilder create(){
    return new PagamentoBuilder();
  }
  public PagamentoBuilder withId(long id){
    this.pagamento.setId(id);
    return this;
  }
  public PagamentoBuilder withValor(double valor){
    this.pagamento.setValor(valor);
    return this;
  }
  public PagamentoBuilder withData(String data){
    this.pagamento.setData(data);
    return this;
  }
  public PagamentoBuilder withUsuario(User usuario){
    this.pagamento.setUsuario(usuario);
    return this;
  }
  public Pagamento build(){
    return this.pagamento;
  }
}

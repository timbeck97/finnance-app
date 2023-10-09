package com.finance.service;

import com.finance.enums.ETipoMovimentacao;
import com.finance.model.*;
import com.finance.repository.MovimentacaoSaldoRepository;
import com.finance.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SaldoService {



  private final MovimentacaoSaldoRepository movimentacaoSaldoRepository;

  private final UserRepository userRepository;

  private final Utils utils;

  public SaldoService(MovimentacaoSaldoRepository movimentacaoSaldoRepository, UserRepository userRepository, Utils utils) {
    this.movimentacaoSaldoRepository = movimentacaoSaldoRepository;
    this.userRepository = userRepository;
    this.utils = utils;
  }

  public void atualizarSaldo(Gasto gasto){
    MovimentacaoSaldo dto= getMovimentoSaldoDefault(gasto.getValor(), false);
    dto.setGasto(gasto);
    dto.setTipoMovimentacao(ETipoMovimentacao.GASTO);
    dto.setDescricao("DEBITO DO GASTO: "+gasto.getDescricao());
    movimentacaoSaldoRepository.save(dto);
  }
  public void atualizarSaldo(Deposito deposito){
    MovimentacaoSaldo dto= getMovimentoSaldoDefault(deposito.getValor(), true);
    dto.setDeposito(deposito);
    dto.setTipoMovimentacao(ETipoMovimentacao.DEPOSITO);
    dto.setDescricao("DEPOSITO : "+deposito.getDescricao());
    movimentacaoSaldoRepository.save(dto);
  }
  public MovimentacaoSaldo getMovimentoSaldoDefault(double valor, boolean adiciona){
    User user=utils.getUsuarioLogado();
    MovimentacaoSaldo dto=new MovimentacaoSaldo();
    dto.setData(LocalDate.now());
    dto.setSaldoAnterior(user.getContaCorrente());
    dto.setValor(valor);
    if(adiciona){
      dto.setSaldoAtualizado(user.getContaCorrente()+valor);
    }else {
      dto.setSaldoAtualizado(user.getContaCorrente()-valor);
    }
    dto.setUser(user);
    user.setContaCorrente(dto.getSaldoAtualizado());
    userRepository.save(user);
    return dto;
  }
}

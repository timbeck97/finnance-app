package com.finance.service;

import com.finance.enums.ETipoMovimentacao;
import com.finance.model.*;
import com.finance.repository.MovimentacaoSaldoRepository;
import com.finance.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

  @Transactional
  public void atualizarSaldo(Gasto gasto){
    reverterGasto(gasto);//se existir um movimento de saldo para esse gasto, ele é removido
    User user=utils.getUsuarioLogado();
    MovimentacaoSaldo dto=getMovimentoSaldoDefault(gasto.getValor(),user,false);
    dto.setGasto(gasto);
    dto.setTipoMovimentacao(ETipoMovimentacao.GASTO);
    dto.setDescricao("DEBITO DO GASTO: "+gasto.getDescricao());
    movimentacaoSaldoRepository.save(dto);

    atualizaSaldoContaCorrente(gasto.getValor(),user,false);
  }
  @Transactional
  public void atualizarSaldo(Deposito deposito){
    reverterDeposito(deposito);
    User user=utils.getUsuarioLogado();
    MovimentacaoSaldo dto= getMovimentoSaldoDefault(deposito.getValor(),user, true);
    dto.setDeposito(deposito);
    dto.setTipoMovimentacao(ETipoMovimentacao.DEPOSITO);
    dto.setDescricao("DEPOSITO : "+deposito.getDescricao());
    movimentacaoSaldoRepository.save(dto);
    atualizaSaldoContaCorrente(deposito.getValor(),user,true);
  }
  public MovimentacaoSaldo getMovimentoSaldoDefault(double valor, User user, boolean adicionar){
    MovimentacaoSaldo dto=new MovimentacaoSaldo();
    dto.setData(LocalDate.now());
    dto.setSaldoAnterior(user.getContaCorrente());
    if(adicionar){
      dto.setSaldoAtualizado(user.getContaCorrente()+valor);
    }else{
      dto.setSaldoAtualizado(user.getContaCorrente()-valor);
    }
    dto.setValor(valor);
    dto.setUser(user);
    return dto;
  }

  public void atualizaSaldoContaCorrente(double valor, User user, boolean adiciona){
    if(adiciona){
      user.setContaCorrente(user.getContaCorrente()+valor);
    }else{
      user.setContaCorrente(user.getContaCorrente()-valor);
    }
    userRepository.save(user);
  }
  public void reverterGasto(Gasto gasto){
    if(gasto.getId()!=null){
      MovimentacaoSaldo dto=movimentacaoSaldoRepository.findByGasto(gasto);
      if(dto!=null){
        double valor=dto.getValor();
        movimentacaoSaldoRepository.delete(dto);
        atualizaSaldoContaCorrente(valor,utils.getUsuarioLogado(),true);
      }
    }
  }
  public void reverterDeposito(Deposito deposito){
    if(deposito.getId()!=null){
      MovimentacaoSaldo dto=movimentacaoSaldoRepository.findByDeposito(deposito);
      if(dto!=null){
        double valor=dto.getValor();
        movimentacaoSaldoRepository.delete(dto);
        atualizaSaldoContaCorrente(valor,utils.getUsuarioLogado(),false);
      }
    }
  }
}

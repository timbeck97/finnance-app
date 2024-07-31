package com.finance.application.service;

import com.finance.application.model.Deposito;
import com.finance.application.model.Gasto;
import com.finance.application.model.MovimentacaoSaldo;
import com.finance.autentication.model.User;
import com.finance.autentication.service.UserService;
import com.finance.configuration.Utils;
import com.finance.configuration.enums.ETipoMovimentacao;
import com.finance.application.repository.MovimentacaoSaldoRepository;
import com.finance.autentication.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
public class SaldoService {



  private final MovimentacaoSaldoRepository movimentacaoSaldoRepository;
  private final UserService userService;
  private final UserRepository userRepository;



  public SaldoService(MovimentacaoSaldoRepository movimentacaoSaldoRepository, UserRepository userRepository, UserService userService) {
    this.movimentacaoSaldoRepository = movimentacaoSaldoRepository;
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @Transactional
  public void atualizarSaldo(Gasto gasto){
    reverterGasto(gasto);//se existir um movimento de saldo para esse gasto, ele é removido
    User user=userService.getUsuarioLogado();
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
    User user=userService.getUsuarioLogado();
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
    if(valor<0){
      throw new IllegalArgumentException("Valor não pode ser negativo");
    }
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
        atualizaSaldoContaCorrente(valor,userService.getUsuarioLogado(),true);
      }
    }
  }
  public void reverterDeposito(Deposito deposito){
    if(deposito.getId()!=null){
      MovimentacaoSaldo dto=movimentacaoSaldoRepository.findByDeposito(deposito);
      if(dto!=null){
        double valor=dto.getValor();
        movimentacaoSaldoRepository.delete(dto);
        atualizaSaldoContaCorrente(valor,userService.getUsuarioLogado(),false);
      }
    }
  }
}

package com.finance.repository;

import com.finance.model.Deposito;
import com.finance.model.Gasto;
import com.finance.model.MovimentacaoSaldo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoSaldoRepository extends JpaRepository<MovimentacaoSaldo, Long> {

  public MovimentacaoSaldo findByDeposito(Deposito deposito);

  public MovimentacaoSaldo findByGasto(Gasto gasto);
}

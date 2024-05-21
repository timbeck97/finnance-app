package com.finance.application.repository;

import com.finance.application.model.Deposito;
import com.finance.application.model.Gasto;
import com.finance.application.model.MovimentacaoSaldo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoSaldoRepository extends JpaRepository<MovimentacaoSaldo, Long> {

  public MovimentacaoSaldo findByDeposito(Deposito deposito);

  public MovimentacaoSaldo findByGasto(Gasto gasto);
}

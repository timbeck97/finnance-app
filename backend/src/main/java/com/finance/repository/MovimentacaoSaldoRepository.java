package com.finance.repository;

import com.finance.model.MovimentacaoSaldo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoSaldoRepository extends JpaRepository<MovimentacaoSaldo, Long> {
}

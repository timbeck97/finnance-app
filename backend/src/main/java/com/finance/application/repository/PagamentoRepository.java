package com.finance.application.repository;

import com.finance.application.dto.PagamentoDTO;
import com.finance.application.model.Pagamento;
import com.finance.autentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{
  @Query("SELECT new com.finance.application.dto.PagamentoDTO(p) FROM Pagamento p WHERE p.data = ?1 and p.usuario=?2")
  PagamentoDTO findByDataAndUsuario(String data, User usuario);
}

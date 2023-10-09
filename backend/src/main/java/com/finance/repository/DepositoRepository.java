package com.finance.repository;

import com.finance.dto.DepositoDTO;
import com.finance.model.Deposito;
import com.finance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepositoRepository extends JpaRepository<Deposito, Long> {
  @Query(value = "Select new com.finance.dto.DepositoDTO(d) from Deposito d where d.data=:data and d.usuario=:usuario order by d.data desc, d.id desc")
  List<DepositoDTO> findByUsuarioAndData(@Param("usuario") User usuarioLogado, @Param("data") String data);
}

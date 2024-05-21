package com.finance.application.repository;

import com.finance.application.dto.DepositoDTO;
import com.finance.application.model.Deposito;
import com.finance.autentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepositoRepository extends JpaRepository<Deposito, Long> {
  @Query(value = "SELECT new com.finance.application.dto.DepositoDTO(d) from Deposito d where d.data=:data and d.usuario=:usuario order by d.data desc, d.id desc")
  List<DepositoDTO> findByUsuarioAndData(@Param("usuario") User usuarioLogado, @Param("data") String data);
}

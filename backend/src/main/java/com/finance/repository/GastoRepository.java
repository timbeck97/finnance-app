package com.finance.repository;


import com.finance.model.Gasto;
import com.finance.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface GastoRepository extends JpaRepository<Gasto, Long> {

  @Query(value = "Select g from Gasto g where to_char(g.data,'yyyyMM')=:data and usuario=:usuario")
  Page<Gasto> findByData(@Param("data")String data, @Param("usuario") User user, Pageable pageable);
}

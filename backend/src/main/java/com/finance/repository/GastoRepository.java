package com.finance.repository;


import com.finance.dto.GastoDTO;
import com.finance.enums.ETipoGasto;
import com.finance.model.Gasto;
import com.finance.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GastoRepository extends JpaRepository<Gasto, Long> {

  @Query(value = "Select new com.finance.dto.GastoDTO(g) from Gasto g where " +
    " to_char(g.data,'yyyyMM')=:data " +
    "and usuario=:usuario " +
    "and g.tipoGasto=:tipoGasto " +
    "and (:filtro is null or lower(g.descricao) like lower(concat('%',:filtro,'%'))) " +
    "order by g.data desc, g.id asc")
  Page<GastoDTO> findByData(@Param("data")String data,@Param("filtro")String filtro, @Param("usuario") User user, @Param("tipoGasto") ETipoGasto tipoGasto, Pageable pageable);

  @Query(value = "Select g from Gasto g where " +
    " to_char(g.data,'yyyyMM')=:data " +
    "and usuario=:usuario " +
    "and g.tipoGasto=:tipoGasto  " +
    "order by g.data desc, g.id asc")
  List<Gasto> findByData(@Param("data")String data, @Param("usuario") User user, @Param("tipoGasto") ETipoGasto tipoGasto);


  @Query(value = "SELECT g from Gasto g where g.descricao=:descricao and to_char(g.data,'yyyyMM')=to_char(now(),'yyyyMM')")
  List<Gasto> findByDescricao(String descricao);
}

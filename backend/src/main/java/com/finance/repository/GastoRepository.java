package com.finance.repository;


import com.finance.dto.GastoDTO;
import com.finance.enums.EFormaPagamento;
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
    "g.data=:data " +
    "and usuario=:usuario " +
    "and g.tipoGasto=:tipoGasto " +
    "and (:filtro is null or lower(g.descricao) like lower(concat('%',:filtro,'%'))) " +
    "order by g.id asc")
  Page<GastoDTO> findByData(@Param("data")String data,@Param("filtro")String filtro, @Param("usuario") User user, @Param("tipoGasto") ETipoGasto tipoGasto, Pageable pageable);

  @Query(value = "Select g from Gasto g where " +
    " g.data=:data " +
    "and usuario=:usuario " +
    "and g.tipoGasto=:tipoGasto  " +
    "order by g.data desc, g.id asc")
  List<Gasto> findByData(@Param("data")String data, @Param("usuario") User user, @Param("tipoGasto") ETipoGasto tipoGasto);


  @Query(value = "SELECT g from Gasto g where g.descricao=:descricao and g.data=to_char(now(),'yyyyMM')")
  List<Gasto> findByDescricao(String descricao);


  @Query(value = "select " +
    " sum(case when g.forma_pagamento='PIX' then g.valor else 0 end) as total_pix, " +
    " sum(case when g.forma_pagamento='CARTAO' then g.valor else 0 end) as total_cartao" +
    " from gasto g where g.data = :anoMes and g.tipo_gasto='VARIAVEL' and g.usuario_id=:usuario" +
    " ",nativeQuery = true)
  List findGastosMensais(@Param("anoMes")String anoMes, @Param("usuario")Long usuario);


  @Query(value = "select g from Gasto g where g.usuario=:usuario  and g.data=:competencia and g.tipoGasto=:tipoGasto")
  List<Gasto> findAllEncerramento(@Param("usuario") User user, @Param("competencia")String competencia, @Param("tipoGasto")ETipoGasto tipoGasto);

  @Query(value = "select g from Gasto g where g.usuario=:usuario and encerrado is true  and g.data =:competencia and g.tipoGasto=:tipoGasto")
  List<Gasto> findAllEncerrados(@Param("usuario") User user, @Param("competencia")String competencia, @Param("tipoGasto")ETipoGasto tipoGasto);
}

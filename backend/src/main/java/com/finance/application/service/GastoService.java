package com.finance.application.service;

import com.finance.application.builder.GastoBuilder;
import com.finance.application.dto.GastoDTO;
import com.finance.application.model.Gasto;
import com.finance.application.repository.GastoRepository;
import com.finance.autentication.model.User;
import com.finance.autentication.service.UserService;
import com.finance.configuration.Utils;
import com.finance.configuration.enums.EFormaPagamento;
import com.finance.configuration.enums.ETipoGasto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GastoService {

  private final GastoRepository gastoRepository;
  private final UserService userService;
  private final SaldoService saldoService;

  public GastoService(GastoRepository gastoRepository, UserService userService, SaldoService saldoService) {
    this.gastoRepository = gastoRepository;
    this.userService = userService;
    this.saldoService = saldoService;
  }


  public List<GastoDTO> findAll(String anoMes, int pageNumber, int pageSize, ETipoGasto tipoGasto, String filtro, HttpServletResponse resp) {
    if (anoMes == null) {
      anoMes = Utils.getAnoMesAtual();
    }
    Pageable page = PageRequest.of(pageNumber - 1, pageSize);
    Page<GastoDTO> gastos = gastoRepository.findByData(anoMes, filtro, userService.getUsuarioLogado(), tipoGasto, page);
    if(resp!=null){
      resp.setHeader("X-Total-Count", String.valueOf(gastos.getTotalElements()));
    }
    return gastos.getContent();
  }

  public Map<String, Double> findAllMensal(String anoMes) {
    if (anoMes == null) {
      anoMes = Utils.getAnoMesAtual();
    }
    User usuarioLogado = userService.getUsuarioLogado();
    List result = gastoRepository.findGastosMensais(anoMes, usuarioLogado.getId());
    Map<String, Double> values = new HashMap<>();
    for (Object o : result) {
      Object[] columns = (Object[]) o;
      if (columns[0] == null) break;
      values.put("totalPix", ((BigDecimal) columns[0]).doubleValue());
      values.put("totalCartao", ((BigDecimal) columns[1]).doubleValue());
    }
    return values;
  }

  public Gasto save(GastoDTO dto) {
    Gasto gasto = GastoBuilder.create()
      .withCategoria(dto.getCategoria())
      .withData(dto.getData())
      .withDescricao(dto.getDescricao())
      .withUsuario(userService.getUsuarioLogado())
      .withFormaPagamento(dto.getFormaPagamento())
      .withValor(dto.getValor())
      .withTipoGasto(dto.getTipoGasto())
      .withEncerrado(dto.getFormaPagamento().equals(EFormaPagamento.PIX))
      .build();
    gasto = gastoRepository.save(gasto);
    if (gasto.getFormaPagamento().equals(EFormaPagamento.PIX)) {
      saldoService.atualizarSaldo(gasto);
    }
    return gasto;
  }

  public Gasto updateGasto(GastoDTO dto, long id) {
    Gasto gasto = gastoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Recurso não encontrado"));
    gasto = GastoBuilder.create(gasto).
      withCategoria(dto.getCategoria()).
      withData(dto.getData()).
      withDescricao(dto.getDescricao()).
      withUsuario(userService.getUsuarioLogado()).
      withFormaPagamento(dto.getFormaPagamento()).
      withValor(dto.getValor()).
      withTipoGasto(dto.getTipoGasto()).
      withEncerrado(dto.getFormaPagamento().equals(EFormaPagamento.PIX)).
      build();

    gasto = gastoRepository.save(gasto);
    if (gasto.getFormaPagamento().equals(EFormaPagamento.PIX)) { //gasto anterior
      if (dto.getFormaPagamento().equals(EFormaPagamento.CARTAO)) { //gasto novo atualizado
        saldoService.reverterGasto(gasto);
      } else {
        saldoService.atualizarSaldo(gasto);
      }
    } else {
      if (dto.getFormaPagamento().equals(EFormaPagamento.PIX)) {
        saldoService.atualizarSaldo(gasto);
      }
    }
    return gasto;
  }

  public void copiarGastosFixos() {
    User user = userService.getUsuarioLogado();
    String mesAnterior = Utils.getAnoMesAnterior();
    List<Gasto> gastos = gastoRepository.findByData(mesAnterior, user, ETipoGasto.FIXO);
    for (Gasto dto : gastos) {
      List<Gasto> existentes = gastoRepository.findByDescricao(dto.getDescricao());
      if (existentes.size() > 0) continue;
      Gasto gasto = GastoBuilder.
        create().
        withCategoria(dto.getCategoria()).
        withData(Utils.getAnoMesAtual()).
        withDescricao(dto.getDescricao()).
        withUsuario(userService.getUsuarioLogado()).
        withFormaPagamento(dto.getFormaPagamento()).
        withValor(dto.getValor()).
        withTipoGasto(dto.getTipoGasto()).
        build();
      gastoRepository.save(gasto);
    }
  }

  public List<GastoDTO> findAllAutoComplete(String competencia, String filtro, Long id) {
    List<GastoDTO> gastos = null;
    if (id != null) {
      gastos = Arrays.asList(new GastoDTO(gastoRepository.findById(id).get()));
    } else {
      Pageable page = PageRequest.of(0, 5);
      gastos = gastoRepository.findByData(competencia, filtro, userService.getUsuarioLogado(), ETipoGasto.VARIAVEL, page).getContent();
    }
    return gastos;
  }
}

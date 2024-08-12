package com.finance.application.service;

import com.finance.application.dto.*;
import com.finance.application.model.Gasto;
import com.finance.application.repository.DepositoRepository;
import com.finance.application.repository.GastoRepository;
import com.finance.application.repository.PagamentoRepository;
import com.finance.autentication.model.User;
import com.finance.autentication.service.UserService;
import com.finance.configuration.Utils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {
  private final GastoRepository gastoRepository;
  private final DepositoRepository depositoRepository;
  private final PagamentoRepository pagamentoRepository;
  private final UserService userService;

  public DashboardService(GastoRepository gastoRepository, DepositoRepository depositoRepository, PagamentoRepository pagamentoRepository, UserService userService) {
    this.gastoRepository = gastoRepository;
    this.depositoRepository = depositoRepository;
    this.pagamentoRepository = pagamentoRepository;
    this.userService = userService;
  }

  public DashboardMensalDTO getDashboardMensal(String ano, String mes) {
    DashboardMensalDTO result = new DashboardMensalDTO();

    String competencia = ano + (mes.length()<2?"0"+mes:mes);
    User user = userService.getUsuarioLogado();
    List<Gasto> gastos = gastoRepository.findByData(competencia, user, null);
    gastos.stream().collect(Collectors.groupingBy(Gasto::getCategoria)).forEach((categoria, gastosCategoria) -> {
      ChartDataDto chartDataDto = new ChartDataDto();
      chartDataDto.setName(categoria.getDescricao());
      chartDataDto.setValue(gastosCategoria.stream().mapToDouble(Gasto::getValor).sum());
      result.getBarChartData().add(chartDataDto);
    });
    double totalGasto = gastos.stream().mapToDouble(Gasto::getValor).sum();
    double totalGastoMesAnterior = gastoRepository.findByData(Utils.getAnoMesAnterior(ano, mes), user, null).stream().mapToDouble(Gasto::getValor).sum();
    double percentualGasto = 0;
    if(totalGastoMesAnterior>0){
      percentualGasto = totalGasto/totalGastoMesAnterior;
    }
    result.setTotalGasto(new PanelValorTotal(totalGastoMesAnterior, totalGasto>totalGastoMesAnterior, percentualGasto));

    double totalReceita = pagamentoRepository.findByDataAndUsuario(competencia, user).getValor();
    double totalReceitaMesAnterior = pagamentoRepository.findByDataAndUsuario(Utils.getAnoMesAnterior(ano, mes), user).getValor();

    double percentualPagamento = 0;
    if(totalReceitaMesAnterior>0){
      percentualPagamento = totalReceita/totalReceitaMesAnterior;
    }
    result.setTotalReceita(new PanelValorTotal(totalReceitaMesAnterior, totalReceita>totalReceitaMesAnterior, percentualPagamento));
    return result;
  }
  public DashboardAnualDTO getDashboardAnual(String ano) {
    DashboardAnualDTO result = new DashboardAnualDTO();
    List<LineChartDto> lineCharArray=new ArrayList<>();
    User user = userService.getUsuarioLogado();
    List<Gasto> gastos = gastoRepository.findByAno(ano, user);
    LineChartDto lineCharGastos = new LineChartDto();
    lineCharGastos.setName("Gasto Mensal");
    gastos.stream().collect(Collectors.groupingBy(Gasto::getData)).forEach((data, gastosMensais) -> {
      ChartDataDto chartDataDto = new ChartDataDto();
      chartDataDto.setName(data);
      chartDataDto.setValue(gastosMensais.stream().mapToDouble(Gasto::getValor).sum());
      lineCharGastos.getSeries().add(chartDataDto);
    });
    result.getLineChartDto().add(lineCharGastos);

    LineChartDto lineCharPagamento = new LineChartDto();
    lineCharPagamento.setName("Renda Mensal");
    List<PagamentoDTO> pagamentos = pagamentoRepository.findByAnoAndUsuario(ano, user);
    pagamentos.forEach(pagamento -> {
      ChartDataDto chartDataDto = new ChartDataDto();
      chartDataDto.setName(pagamento.getData());
      chartDataDto.setValue(new BigDecimal(pagamento.getValor()));
      lineCharPagamento.getSeries().add(chartDataDto);
    });
    result.getLineChartDto().add(lineCharPagamento);

    gastos.stream().collect(Collectors.groupingBy(Gasto::getCategoria)).forEach((categoria, gastosCategoria) -> {
      ChartDataDto chartDataDto = new ChartDataDto();
      chartDataDto.setName(categoria.getDescricao());
      chartDataDto.setValue(gastosCategoria.stream().mapToDouble(Gasto::getValor).sum());
      result.getBarChartData().add(chartDataDto);
    });
    double totalAnoAtual=gastos.stream().mapToDouble(Gasto::getValor).sum();
    double totalAnoAnterior = gastoRepository.findByAno(Utils.getAnoAnterior(ano), user).stream().mapToDouble(Gasto::getValor).sum();

    double percentualGasto = 0;
    if(totalAnoAnterior>0){
      percentualGasto = totalAnoAtual/totalAnoAnterior;
    }
    result.setTotalGasto(new PanelValorTotal(totalAnoAnterior, totalAnoAtual>totalAnoAnterior, percentualGasto));

    double pagamentoAtual = pagamentos.stream().mapToDouble(PagamentoDTO::getValor).sum();
    double pagamentoAnterior = pagamentoRepository.findByAnoAndUsuario(Utils.getAnoAnterior(ano), user).stream().mapToDouble(PagamentoDTO::getValor).sum();
    double percentual = 0;
    if(pagamentoAnterior>0){
      percentual = pagamentoAtual/pagamentoAnterior;
    }
    result.setTotalReceita(new PanelValorTotal(pagamentoAnterior, pagamentoAtual>pagamentoAnterior, percentual));
    return result;
  }

}

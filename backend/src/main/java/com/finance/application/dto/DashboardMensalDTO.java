package com.finance.application.dto;

import java.util.ArrayList;
import java.util.List;

public class DashboardMensalDTO {

  private List<ChartDataDto> barChartData;
  private PanelValorTotal totalGasto;
  private PanelValorTotal totalReceita;


  public DashboardMensalDTO() {
  }

  public List<ChartDataDto> getBarChartData() {
    if(barChartData==null){
      barChartData=new ArrayList<>();
    }
    return barChartData;
  }

  public void setBarChartData(List<ChartDataDto> barChartData) {
    this.barChartData = barChartData;
  }

  public PanelValorTotal getTotalGasto() {
    return totalGasto;
  }

  public void setTotalGasto(PanelValorTotal totalGasto) {
    this.totalGasto = totalGasto;
  }

  public PanelValorTotal getTotalReceita() {
    return totalReceita;
  }

  public void setTotalReceita(PanelValorTotal totalReceita) {
    this.totalReceita = totalReceita;
  }
}

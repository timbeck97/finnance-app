package com.finance.application.dto;

import java.util.List;

public class DashboardAnualDTO {

  private List<LineChartDto> lineChartDto;
  private List<ChartDataDto> barChartData;
  private PanelValorTotal totalGasto;
  private PanelValorTotal totalReceita;


  public DashboardAnualDTO() {
  }

  public List<LineChartDto> getLineChartDto() {
    if(lineChartDto==null){
      lineChartDto=new java.util.ArrayList<>();
    }
    return lineChartDto;
  }

  public void setLineChartDto(List<LineChartDto> lineChartDto) {
    this.lineChartDto = lineChartDto;
  }

  public List<ChartDataDto> getBarChartData() {
    if(barChartData==null){
      barChartData=new java.util.ArrayList<>();
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


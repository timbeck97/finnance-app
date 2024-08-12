package com.finance.application.dto;

import java.util.ArrayList;
import java.util.List;

public class LineChartDto {

  private String name;
  private List<ChartDataDto> series;

  public LineChartDto() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<ChartDataDto> getSeries() {
    if(series==null){
      series=new ArrayList<>();
    }
    return series;
  }

  public void setSeries(List<ChartDataDto> series) {
    this.series = series;
  }
}

package com.finance.application.dto;

public class ChartDataDto {

  private String name;
  private Number value;

  public ChartDataDto() {
  }

  public ChartDataDto(String name, double value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Number getValue() {
    return value;
  }

  public void setValue(Number value) {
    this.value = value;
  }
}

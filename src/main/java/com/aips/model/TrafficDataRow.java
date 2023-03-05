package com.aips.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TrafficDataRow {
  private LocalDateTime dateTime;
  private LocalDate date;
  private Integer count;

  public TrafficDataRow(LocalDateTime dateTime, Integer count) {
    this.dateTime=dateTime;
    this.count=count;
    this.date=dateTime.toLocalDate();
  }
}

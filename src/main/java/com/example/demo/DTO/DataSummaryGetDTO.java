package com.example.demo.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataSummaryGetDTO {
  private String name;
  private String symbol;
  private double price;
  private double priceDifferenceInTwentyFourHours;
  private double priceDifferenceInSevenDays;
  private double priceDifferenceInOneMonth;
  private double volumeInTwentyFourHours;
  private double marketCap;
}

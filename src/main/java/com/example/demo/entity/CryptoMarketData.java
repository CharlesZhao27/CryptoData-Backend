package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "crypto_market_data")
public class CryptoMarketData implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private Integer id;

  @Column(name = "name", nullable = false)
  @CsvBindByName(column = "Name")
  private String name;

  @Column(name = "symbol", nullable = false)
  @CsvBindByName(column = "Symbol")
  private String symbol;

  @Column(name = "date", nullable = false)
  @CsvBindByName(column = "Date")
  @CsvDate("yyyy-MM-dd HH:mm:ss")
  private Timestamp date;

  @Column(name = "high")
  @CsvBindByName(column = "High")
  private BigDecimal high;

  @Column(name = "low")
  @CsvBindByName(column = "Low")
  private BigDecimal low;

  @Column(name = "open")
  @CsvBindByName(column = "Open")
  private BigDecimal open;

  @Column(name = "close")
  @CsvBindByName(column = "Close")
  private BigDecimal close;

  @Column(name = "volume")
  @CsvBindByName(column = "Volume")
  private BigDecimal volume;

  @Column(name = "market_cap")
  @CsvBindByName(column = "Marketcap")
  private BigDecimal market_cap;

}

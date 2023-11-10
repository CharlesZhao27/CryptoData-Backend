package com.example.demo.service;

import com.example.demo.entity.CryptoMarketData;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvToBeanTest {

  @Test
  public void testCsvToBean(){
    String csvData = "Name,Symbol,Date,High,Low,Open,Close,Volume,Marketcap\n"
        + "NEM,XEM,2015-04-02 23:59:59,0.000323,0.000227,0.000242,0.000313,28549,2823534\n";

    StringReader stringReader = new StringReader(csvData);
    CSVReader csvReader = new CSVReaderBuilder(stringReader)
        .build();

    CsvToBean<CryptoMarketData> csvToBean = new CsvToBeanBuilder<CryptoMarketData>(csvReader)
        .withType(CryptoMarketData.class)
        .withThrowExceptions(false)
        .build();

    List<CryptoMarketData> beans = csvToBean.parse();

    CryptoMarketData data = beans.get(0);

    assertEquals(1, beans.size());
    assertEquals("NEM", data.getName());
    assertEquals("XEM", data.getSymbol());
  }
}

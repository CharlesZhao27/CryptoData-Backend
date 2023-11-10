package com.example.demo.service;

import com.example.demo.entity.CryptoMarketData;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CryptoMarketDataLoader {

  private ResourceLoader resourceLoader;
  public List<CryptoMarketData> loadCryptoMarketData() throws IOException {
    List<CryptoMarketData> marketDataList = new ArrayList<>();
    String directoryPath = "classpath:static/crypto-dataset";
    Resource directoryResource = resourceLoader.getResource(directoryPath);

    Files.list(Paths.get(directoryResource.getURI()))
        .filter(path -> path.toString().endsWith(".csv"))
        .forEach(file -> {
          try {
            Reader reader = Files.newBufferedReader(file);
            CSVReader csvReader = new CSVReaderBuilder(reader)
                .build();

            CsvToBean<CryptoMarketData> csvToBean = new CsvToBeanBuilder<CryptoMarketData>(csvReader)
                .withType(CryptoMarketData.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

            marketDataList.addAll(csvToBean.parse());

          } catch (IOException e){
            e.printStackTrace();
          }
        });

    return marketDataList;
  }
}

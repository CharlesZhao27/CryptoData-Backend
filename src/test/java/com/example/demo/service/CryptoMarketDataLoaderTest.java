package com.example.demo.service;


import com.example.demo.entity.CryptoMarketData;
import com.example.demo.service.CryptoMarketDataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CryptoMarketDataLoaderTest {

  @Autowired
  private ResourceLoader resourceLoader;
  private CryptoMarketDataLoader dataLoader;

  @BeforeEach
  public void setUp(){
    dataLoader = new CryptoMarketDataLoader(resourceLoader);
  }

  @Test
  public void shouldLoadAllCryptoMarketData() throws IOException{
    String directoryPath = "classpath:static/crypto-dataset";
    Resource directoryResource = resourceLoader.getResource(directoryPath);
    long expectedDataCount = Files.list(Paths.get(directoryResource.getURI()))
        .filter(path -> path.toString().endsWith(".csv"))
        .count();

    List<CryptoMarketData> dataList = dataLoader.loadCryptoMarketData();

    assertNotNull(dataList, "Data list should not be null");
    dataList.forEach(data -> {
      assertNotNull(data.getName(), "Data name should not be null");
      assertNotNull(data.getSymbol(), "Data symbol should not be null");
      assertNotNull(data.getDate(), "Data time should not be null");
    });

  }

}

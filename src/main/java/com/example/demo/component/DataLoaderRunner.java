package com.example.demo.component;

import com.example.demo.entity.CryptoMarketData;
import com.example.demo.repository.CryptoMarketDataRepository;
import com.example.demo.service.CryptoMarketDataLoader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoaderRunner implements CommandLineRunner {
  private final CryptoMarketDataLoader cryptoMarketDataLoader;
  private final CryptoMarketDataRepository cryptoMarketDataRepository;


  @Override
  @Transactional
  public void run(String... args) throws Exception {
    List<CryptoMarketData> marketDataList = cryptoMarketDataLoader.loadCryptoMarketData();

//    for(CryptoMarketData data : marketDataList){
//      if(!cryptoMarketDataRepository.existsByNameAndDate(
//          data.getName(),
//          data.getDate()
//      )){
//        cryptoMarketDataRepository.save(data);
//      }
//    }
    cryptoMarketDataRepository.saveAll(marketDataList);
  }
}

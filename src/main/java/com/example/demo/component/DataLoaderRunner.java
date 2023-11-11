package com.example.demo.component;

import com.example.demo.entity.CryptoMarketData;
import com.example.demo.repository.CryptoMarketDataRepository;
import com.example.demo.service.CryptoMarketDataCSVLoadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataLoaderRunner implements CommandLineRunner {
  private final CryptoMarketDataCSVLoadService cryptoMarketDataCSVLoadService;
  private final CryptoMarketDataRepository cryptoMarketDataRepository;

  @Autowired
  private RedisTemplate<String, List<CryptoMarketData>> redisTemplate;


  @Override
  @Transactional
  public void run(String... args) throws Exception {
    List<CryptoMarketData> marketDataList = cryptoMarketDataCSVLoadService.loadCryptoMarketData();

    //Add Data to Database
    cryptoMarketDataRepository.saveAll(marketDataList);

    //Add Most Recent Data to Redis
    Map<String, List<CryptoMarketData>> groupedData = marketDataList.stream()
        .collect(Collectors.groupingBy(CryptoMarketData::getName));

    groupedData.forEach((name, list) -> {
      List<CryptoMarketData> recentMarketData = list.stream()
          .sorted(Comparator.comparing(CryptoMarketData::getDate).reversed())
          .limit(7)
          .toList();
      redisTemplate.opsForValue().set(name, recentMarketData);
    });

  }
}

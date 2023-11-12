package com.example.demo.component;

import com.example.demo.entity.CryptoMarketData;
import com.example.demo.repository.CryptoMarketDataRepository;
import com.example.demo.service.CryptoMarketDataCSVLoadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;


  /* Load Data to the Database and Redis only when database is empty
  * */
  @Override
  @Transactional
  public void run(String... args) throws Exception {
    if(cryptoMarketDataRepository.count() == 0){
      List<CryptoMarketData> marketDataList = cryptoMarketDataCSVLoadService.loadCryptoMarketData();
      //Add Data to Database
      cryptoMarketDataRepository.saveAll(marketDataList);
      //Add Most Recent Data to Redis
      processAndAddDataToRedis(marketDataList);
    }
  }

  private void processAndAddDataToRedis(List<CryptoMarketData> marketDataList) {
    Map<String, List<CryptoMarketData>> groupedData = marketDataList.stream()
        .collect(Collectors.groupingBy(CryptoMarketData::getName));

    groupedData.forEach((name, list) -> {
      List<CryptoMarketData> recentMarketData = list.stream()
          .sorted(Comparator.comparing(CryptoMarketData::getDate).reversed())
          .limit(30)
          .toList();
      try{
        String jsonData = objectMapper.writeValueAsString(recentMarketData);
        redisTemplate.opsForValue().set(name, jsonData);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    });
  }
}

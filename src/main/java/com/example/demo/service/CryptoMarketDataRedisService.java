package com.example.demo.service;

import com.example.demo.entity.CryptoMarketData;
import com.example.demo.repository.CryptoMarketDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoMarketDataRedisService {

  @Autowired
  private CryptoMarketDataRepository cryptoMarketDataRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  /**
  * Fetch Recent Data from Database to Redis
  */
  public void loadAllRecentData() {
    List<String> allCryptoDataNames = cryptoMarketDataRepository.findDistinctNames();
    for(String name : allCryptoDataNames){
      List<CryptoMarketData> marketDataList = cryptoMarketDataRepository.findAllByNameOrderByDateDesc(name);
      List<CryptoMarketData> recentMarketData = marketDataList.size() > 30 ? marketDataList.subList(0, 29) : marketDataList;
      try{
        String jsonData = objectMapper.writeValueAsString(recentMarketData);
        redisTemplate.opsForValue().set(name, jsonData);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }



}

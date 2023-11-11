package com.example.demo.service;

import com.example.demo.entity.CryptoMarketData;
import com.example.demo.repository.CryptoMarketDataRepository;
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
  private RedisTemplate<String, List<CryptoMarketData>> redisTemplate;

  @PostConstruct
  public void loadAllRecentData() {

    List<String> allCryptoDataNames = cryptoMarketDataRepository.findDistinctNames();
    for(String name : allCryptoDataNames){
      List<CryptoMarketData> marketDataList = cryptoMarketDataRepository.findAllByNameOrderByDateDesc(name);
      List<CryptoMarketData> recentMarketData = marketDataList.size() > 7 ? marketDataList.subList(0, 7) : marketDataList;
      redisTemplate.opsForValue().set(name, recentMarketData);
    }
  }



}

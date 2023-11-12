package com.example.demo.service;

import com.example.demo.DTO.DataSummaryGetDTO;
import com.example.demo.entity.CryptoMarketData;
import com.example.demo.repository.CryptoMarketDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DataService {

  @Autowired
  private CryptoMarketDataRepository cryptoMarketDataRepository;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  /*
  Read Data from Redis and summarize data
  * */
  public List<DataSummaryGetDTO> getRecentMarketDataSummary() {

    List<DataSummaryGetDTO> summaryList = new ArrayList<>();

    List<String> allCryptoDataNames = cryptoMarketDataRepository.findDistinctNames();
    for(String name : allCryptoDataNames){
      String marketDataJson = redisTemplate.opsForValue().get(name);
      if(marketDataJson != null && !marketDataJson.isEmpty()){
        try {
          List<CryptoMarketData> marketDataList = objectMapper.readValue(marketDataJson, new TypeReference<List<CryptoMarketData>>() {});
                DataSummaryGetDTO dataSummaryGetDTO = getSummarizedData(marketDataList);
                summaryList.add(dataSummaryGetDTO);
        } catch (JsonProcessingException e){
          e.printStackTrace();
        }
      }
    }
    summaryList.sort(Comparator.comparing(DataSummaryGetDTO::getMarketCap).reversed());
    return summaryList;
  }

  private DataSummaryGetDTO getSummarizedData(List<CryptoMarketData> marketDataList) {
    if(marketDataList==null || marketDataList.isEmpty()){
      throw new RuntimeException("no Such Resource");
    }
    marketDataList.sort(Comparator.comparing(CryptoMarketData::getDate).reversed());


    double priceChangeInTwentyFourHours = marketDataList.get(0).getClose()
        .subtract(marketDataList.get(1).getClose())
        .doubleValue();

    double priceChangeInSevenDays = marketDataList.get(0).getClose()
        .subtract(marketDataList.get(6).getClose())
        .doubleValue();

    double priceChangeInOneMonth = marketDataList.get(0).getClose()
        .subtract(marketDataList.get(29).getClose())
        .doubleValue();

    double volumeChangeInTwentyFourHours = marketDataList.get(0).getVolume()
        .subtract(marketDataList.get(1).getVolume())
        .doubleValue();

    double marketCap = marketDataList.get(0).getMarket_cap()
        .doubleValue();

    return DataSummaryGetDTO.builder()
        .name(marketDataList.get(0).getName())
        .symbol(marketDataList.get(0).getSymbol())
        .price(marketDataList.get(0).getClose().doubleValue())
        .priceDifferenceInTwentyFourHours(priceChangeInTwentyFourHours)
        .priceDifferenceInSevenDays(priceChangeInSevenDays)
        .priceDifferenceInOneMonth(priceChangeInOneMonth)
        .volumeInTwentyFourHours(volumeChangeInTwentyFourHours)
        .marketCap(marketCap)
        .build();
  }

  public List<CryptoMarketData> getRecentMarketDataByName(String name) {
    String marketDataJson = redisTemplate.opsForValue().get(name);
    List<CryptoMarketData> marketDataList = null;
    if (marketDataJson != null && !marketDataJson.isEmpty()) {
      try {
        marketDataList = objectMapper.readValue(marketDataJson, new TypeReference<List<CryptoMarketData>>() {
        });
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    return marketDataList;
  }

}

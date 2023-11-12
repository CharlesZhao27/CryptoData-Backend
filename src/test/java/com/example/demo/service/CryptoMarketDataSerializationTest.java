package com.example.demo.service;

import com.example.demo.entity.CryptoMarketData;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CryptoMarketDataSerializationTest {

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp(){
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.activateDefaultTyping(new LaissezFaireSubTypeValidator(),ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
  }

  @Test
  public void testSerializationAndDeserialization() throws Exception{
    CryptoMarketData mockData = new CryptoMarketData();
    mockData.setId(1);
    mockData.setName("Bitcoin");
    mockData.setSymbol("BTC");
    mockData.setDate(Timestamp.valueOf("2013-04-29 23:59:59"));
    mockData.setHigh(new BigDecimal("147.48800659179688"));
    mockData.setLow(new BigDecimal("134.0"));
    mockData.setOpen(new BigDecimal("134.44400024414062"));
    mockData.setClose(new BigDecimal("144.5399932861328"));
    mockData.setVolume(new BigDecimal("0.0"));
    mockData.setMarket_cap(new BigDecimal("1603768864.5"));

    //serialization
    String json = objectMapper.writeValueAsString(mockData);

    //deserialization
    CryptoMarketData deserializedData = objectMapper.readValue(json, CryptoMarketData.class);

    assertEquals(mockData.getId(), deserializedData.getId());
    assertEquals(mockData.getName(), deserializedData.getName());
    assertEquals(mockData.getSymbol(), deserializedData.getSymbol());
    assertEquals(mockData.getDate(), deserializedData.getDate());
    assertEquals(mockData.getHigh(), deserializedData.getHigh());
    assertEquals(mockData.getLow(), deserializedData.getLow());
    assertEquals(mockData.getOpen(), deserializedData.getOpen());
    assertEquals(mockData.getClose(), deserializedData.getClose());
    assertEquals(mockData.getVolume(), deserializedData.getVolume());
    assertEquals(mockData.getMarket_cap(), deserializedData.getMarket_cap());
  }


}

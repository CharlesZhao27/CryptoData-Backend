package com.example.demo.config;

import com.example.demo.entity.CryptoMarketData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate<String, List<CryptoMarketData>> redisTemplate(RedisConnectionFactory redisConnectionFactory){
    RedisTemplate<String, List<CryptoMarketData>> template = new RedisTemplate<>();

    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

    return template;
  }
}

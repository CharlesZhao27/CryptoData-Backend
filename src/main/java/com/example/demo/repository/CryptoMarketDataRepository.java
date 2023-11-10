package com.example.demo.repository;

import com.example.demo.entity.CryptoMarketData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface CryptoMarketDataRepository extends JpaRepository<CryptoMarketData, Integer> {

  boolean existsByNameAndDate(String name, Timestamp date);
}

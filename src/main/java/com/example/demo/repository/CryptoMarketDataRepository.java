package com.example.demo.repository;

import com.example.demo.entity.CryptoMarketData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CryptoMarketDataRepository extends JpaRepository<CryptoMarketData, Integer> {

  boolean existsByNameAndDate(String name, LocalDateTime date);

  @Query("SELECT DISTINCT c.name FROM CryptoMarketData c")
  List<String> findDistinctNames();

  List<CryptoMarketData> findAllByNameOrderByDateDesc(String name);
}

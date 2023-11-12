package com.example.demo.controller;

import com.example.demo.DTO.DataSummaryGetDTO;
import com.example.demo.entity.CryptoMarketData;
import com.example.demo.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {

  @Autowired
  private DataService dataService;

  //fetch all data summary from the server side
  @GetMapping("/data")
  public ResponseEntity<List<DataSummaryGetDTO>> getAllRecentData(){
    List<DataSummaryGetDTO> marketDataSummaryList = dataService.getRecentMarketDataSummary();
    return new ResponseEntity<>(marketDataSummaryList, HttpStatus.OK);
  }

  //fetch specific recent data(30days) from server side
  @GetMapping("/data/{name}")
  public ResponseEntity<List<CryptoMarketData>> getRecentMarketDataByName(@PathVariable String name){
    List<CryptoMarketData> marketDataList = dataService.getRecentMarketDataByName(name);
    if(marketDataList == null){
      return new ResponseEntity<>(marketDataList, HttpStatus.BAD_REQUEST);
    }else{
      return new ResponseEntity<>(marketDataList, HttpStatus.OK);
    }
  }


}

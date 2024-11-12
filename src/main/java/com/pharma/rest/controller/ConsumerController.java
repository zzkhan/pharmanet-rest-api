package com.pharma.rest.controller;

import com.pharma.rest.model.DrugSaleLookupResponse;
import com.pharma.service.ConsumerFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3001")
public class ConsumerController {

  @Autowired
  ConsumerFacade consumerFacade;

  @GetMapping("/consumer/drug/check")
  public ResponseEntity checkDrugProvenance(@RequestParam String retailKey) {
    log.info("lookup key: {}", retailKey);
    byte[] decoded = Base64.getDecoder().decode(retailKey);
    log.info("lookup key decoded: {}", new String(decoded));
    DrugSaleLookupResponse drugRetailResponse = consumerFacade.findDrugRetail(new String(decoded));
    if(drugRetailResponse.isDrugSaleFound()){
      return ResponseEntity.ok(drugRetailResponse);
    }
    return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
  }
}

package com.pharma.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharma.rest.model.DrugSaleLookupResponse;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.client.Gateway;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerFacade {

  final DrugService drugService;

  final EncryptionService encryptionService;
  public ConsumerFacade(Gateway consumerGateway, EncryptionService encryptionService, ObjectMapper objectMapper) {
    this.drugService = DrugService.instance(consumerGateway, objectMapper);
    this.encryptionService = encryptionService;
  }

  public DrugSaleLookupResponse findDrugRetail(String retailKey) {
    try {
      String decryptedKey = encryptionService.decrypt(retailKey);
      log.info("decryptedKey: {}", decryptedKey);
      return drugService.getByRetailKey(decryptedKey);
    } catch (Exception e) {
      log.error("Error occurred fetch drug sale details {}", e.getMessage());
      return DrugSaleLookupResponse.builder().build();
    }
  }
}

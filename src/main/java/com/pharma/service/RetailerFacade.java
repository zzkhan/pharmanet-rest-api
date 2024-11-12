package com.pharma.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharma.rest.model.Challenges;
import com.pharma.rest.model.CreatePurchaseOrder;
import com.pharma.rest.model.Crp;
import com.pharma.rest.model.DrugVerificationOutcome;
import com.pharma.rest.model.PurchaseOrder;
import com.pharma.rest.model.QRCode;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.client.Gateway;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RetailerFacade {

  private final QRCodeGenerator qrCodeGenerator;
  private final EncryptionService encryptionService;
  private final DrugService drugService;
  private final DrugVerificationService drugVerificationService;
  private final PoService poService;
  private final RetailService drugRetailService;

  public RetailerFacade(QRCodeGenerator qrCodeGenerator, EncryptionService encryptionService, Gateway pharmacyGateway, ObjectMapper objectMapper) {
    this.qrCodeGenerator = qrCodeGenerator;
    this.encryptionService = encryptionService;
    this.drugService = DrugService.instance(pharmacyGateway, objectMapper);
    this.poService = PoService.instance(pharmacyGateway);
    this.drugVerificationService = DrugVerificationService.instance(pharmacyGateway);
    this.drugRetailService = RetailService.instance(pharmacyGateway);
  }

  public String getAllDrugs() throws Exception {
    return drugService.getAllDrugs();
  }

  public PurchaseOrder createPurchaseOrder(CreatePurchaseOrder createPurchaseOrder) {
    return poService.createPo(createPurchaseOrder.getSeller(), createPurchaseOrder.getDrugName(), createPurchaseOrder.getQuantity());
  }

  public List<Challenges> getDrugChallenges(String drugName, String tagId) {
    return drugVerificationService.getDrugChallenges(drugName, tagId);
  }

  public void submitVerificationCrps(String drugName, String tagId, List<Crp> crps) {
    drugVerificationService.submitVerificationCrps(drugName, tagId, crps);
  }

  public DrugVerificationOutcome verifyCrps(String drugName, String tagId, List<Crp> crps) {
    return drugVerificationService.verifyCrps(drugName, tagId, crps);
  }

  public QRCode retailDrug(String drugName, String drugId) {
    var retailKey = drugRetailService.retailDrug(drugName, drugId);
    try {
      log.info("retail key {}", retailKey);
      return new QRCode(qrCodeGenerator.generate(encryptionService.encrypt(retailKey)));
    } catch (Exception e) {
      log.error("Error during encryption of drug retail record key.");
      throw new RuntimeException(e);
    }
  }
}

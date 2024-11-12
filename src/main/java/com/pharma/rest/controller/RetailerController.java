package com.pharma.rest.controller;


import com.pharma.rest.model.Challenges;
import com.pharma.rest.model.CreatePurchaseOrder;
import com.pharma.rest.model.DrugRetailPayload;
import com.pharma.rest.model.DrugVerificationOutcome;
import com.pharma.rest.model.DrugVerificationPayload;
import com.pharma.rest.model.PurchaseOrder;
import com.pharma.rest.model.QRCode;
import com.pharma.service.RetailerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
public class RetailerController {

  @Autowired
  RetailerFacade retailerFacade;
  @GetMapping("/retailer/drugs/{drugName}/{tagId}/challenges")
  public ResponseEntity<List<Challenges>> getDrugChallenges(@PathVariable String drugName, @PathVariable String tagId){
    List<Challenges> challenges = retailerFacade.getDrugChallenges(drugName, tagId);
    return ResponseEntity.ok(challenges);
  }
  @GetMapping("/retailer/drugs")
  public ResponseEntity<String> getAllDrugs(){
    try {
      return ResponseEntity.ok(retailerFacade.getAllDrugs());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  @PostMapping(value = "/retailer/po", consumes = "application/json")
  public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody CreatePurchaseOrder createPurchaseOrder){
    try {
      PurchaseOrder purchaseOrder = retailerFacade.createPurchaseOrder(createPurchaseOrder);
      return ResponseEntity.ok(purchaseOrder);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  @PostMapping(value = "/retailer/drugs/verify", consumes = "application/json")
  public ResponseEntity<DrugVerificationOutcome> verifyDrug(@RequestBody DrugVerificationPayload drugVerificationPayload){
    var outcome = retailerFacade.verifyCrps(drugVerificationPayload.getDrugName(), drugVerificationPayload.getTagId(), drugVerificationPayload.getCrps());
    return ResponseEntity.ok(outcome);
  }
  @PostMapping(value = "/retailer/drugs/verification", consumes = "application/json")
  public ResponseEntity<String> submitVerificationCrps(@RequestBody DrugVerificationPayload drugVerificationPayload){
    retailerFacade.submitVerificationCrps(drugVerificationPayload.getDrugName(), drugVerificationPayload.getTagId(), drugVerificationPayload.getCrps());
    return ResponseEntity.ok("submitted");
  }
  @PostMapping(value = "/retailer/drugs/retail", consumes = "application/json")
  public ResponseEntity<QRCode> retailDrug(@RequestBody DrugRetailPayload drugRetailPayload){
    QRCode qrCode = retailerFacade.retailDrug(drugRetailPayload.getDrugName(), drugRetailPayload.getTagId());
    return ResponseEntity.ok(qrCode);
  }
}

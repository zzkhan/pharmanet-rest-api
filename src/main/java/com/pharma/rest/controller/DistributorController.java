package com.pharma.rest.controller;


import com.pharma.rest.model.Challenges;
import com.pharma.rest.model.CreatePurchaseOrder;
import com.pharma.rest.model.CreateShipment;
import com.pharma.rest.model.DrugVerificationOutcome;
import com.pharma.rest.model.DrugVerificationPayload;
import com.pharma.rest.model.PurchaseOrder;
import com.pharma.service.DistributorFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DistributorController {

  @Autowired
  DistributorFacade distributorFacade;
  @GetMapping("/distributor/drugs/{drugName}/{tagId}/challenges")
  public ResponseEntity<List<Challenges>> getDrugChallenges(@PathVariable String drugName, @PathVariable String tagId){
    List<Challenges> challenges = distributorFacade.getDrugChallenges(drugName, tagId);
    return ResponseEntity.ok(challenges);
  }
  @GetMapping("/distributor/drugs")
  public ResponseEntity<String> getAllDrugs(){
    try {
      return ResponseEntity.ok(distributorFacade.getAllDrugs());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  @PostMapping(value = "/distributor/drugs/verify", consumes = "application/json")
  public ResponseEntity<DrugVerificationOutcome> verifyDrug(@RequestBody DrugVerificationPayload drugVerificationPayload){
    var outcome = distributorFacade.verifyCrps(drugVerificationPayload.getDrugName(), drugVerificationPayload.getTagId(), drugVerificationPayload.getCrps());
    return ResponseEntity.ok(outcome);
  }
  @PostMapping(value = "/distributor/drugs/verification", consumes = "application/json")
  public ResponseEntity<String> submitVerificationCrps(@RequestBody DrugVerificationPayload drugVerificationPayload){
    distributorFacade.submitVerificationCrps(drugVerificationPayload.getDrugName(), drugVerificationPayload.getTagId(), drugVerificationPayload.getCrps());
    return ResponseEntity.ok("submitted");
  }
  @PostMapping(value = "/distributor/po", consumes = "application/json")
  public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody CreatePurchaseOrder createPurchaseOrder){
    try {
      PurchaseOrder purchaseOrder = distributorFacade.createPurchaseOrder(createPurchaseOrder);
      return ResponseEntity.ok(purchaseOrder);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  @PostMapping(value = "/distributor/shipments", consumes = "application/json")
  public ResponseEntity<String> createShipment(@RequestBody CreateShipment createShipment) {
    distributorFacade.createShipment(createShipment);
    return ResponseEntity.ok("created");
  }
}

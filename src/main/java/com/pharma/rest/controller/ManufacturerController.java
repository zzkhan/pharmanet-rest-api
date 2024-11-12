package com.pharma.rest.controller;


import com.pharma.rest.model.CreateDrug;
import com.pharma.rest.model.CreateShipment;
import com.pharma.rest.model.Crp;
import com.pharma.rest.model.Drug;
import com.pharma.rest.model.DrugPayload;
import com.pharma.service.ManufacturerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManufacturerController {

  @Autowired
  ManufacturerFacade manufacturerFacade;

  @GetMapping("/manufacturer/drugs")
  public ResponseEntity<String> getAllDrugs() {
    try {
      return ResponseEntity.ok(manufacturerFacade.getAllDrugs());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/manufacturer/drugs/{drugName}/{tagId}")
  public ResponseEntity<List<Crp>> getDrugAssignedCrps(@PathVariable String drugName, @PathVariable String tagId) {
    try {
      return ResponseEntity.ok(manufacturerFacade.getAssignedCrps(drugName, tagId, "Org3MSP"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @PostMapping(value = "/manufacturer/drugs", consumes = "application/json")
  public ResponseEntity<Drug> registerDrug(@RequestBody DrugPayload drugPayload) {
    try {
      Drug registeredDrug = manufacturerFacade
              .registerDrug(CreateDrug.builder()
                      .tagId(drugPayload.getTagId())
                      .drugName(drugPayload.getDrugName())
                      .manufactureDate(drugPayload.getManufactureDate())
                      .expiryDate(drugPayload.getExpiryDate())
                      .build(), drugPayload.getCrps());
      return ResponseEntity.ok(registeredDrug);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @PostMapping(value = "/manufacturer/shipments", consumes = "application/json")
  public ResponseEntity<String> createShipment(@RequestBody CreateShipment createShipment) {
    try {
      String shipment = manufacturerFacade.createShipment(createShipment);
      return ResponseEntity.ok(shipment);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

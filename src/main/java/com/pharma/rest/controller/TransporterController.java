package com.pharma.rest.controller;


import com.pharma.rest.model.CreateShipment;
import com.pharma.service.TransporterFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransporterController {

  @Autowired
  TransporterFacade transporterFacade;

  @PutMapping(value = "/transporter/shipments", consumes = "application/json")
  public ResponseEntity<String> updateShipment(@RequestBody CreateShipment createShipment) {
    transporterFacade.updateShipment(createShipment.getBuyer(), createShipment.getDrugName());
    return ResponseEntity.ok("updated");
  }
}

package com.pharma.rest.controller;


import com.pharma.rest.model.CreateShipment;
import com.pharma.service.TransporterFacade;
import com.pharma.temp.AuthBuilder;
import com.pharma.temp.AuthBuilderFactory;
import com.pharma.temp.Authorisation;
import com.pharma.temp.FiscalAuthorisation;
import com.pharma.temp.TransitAuthorisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransporterController {

  @Autowired
  TransporterFacade transporterFacade;

  @Autowired
  AuthBuilderFactory<Authorisation> authorisationAuthBuilderFactory;

  @PutMapping(value = "/transporter/shipments", consumes = "application/json")
  public ResponseEntity<String> updateShipment(@RequestBody CreateShipment createShipment) {
    transporterFacade.updateShipment(createShipment.getBuyer(), createShipment.getDrugName());
    return ResponseEntity.ok("updated");
  }

  @GetMapping(value = "/temp")
  public String generics(){
    AuthBuilder<Authorisation> builder = authorisationAuthBuilderFactory.getBuilder("F");
    Authorisation authorisation = builder.build();
    processAuth(authorisation);
    System.out.println(authorisation.getClass());
    return authorisation.toString();
  }

  private void processAuth(Authorisation authorisation){
    switch (authorisation){
      case TransitAuthorisation transitAuthorisation -> {

      }
      case FiscalAuthorisation fiscalAuthorisation -> {

      }
      default -> {
        throw new IllegalArgumentException("");
      }
    }
  }
}

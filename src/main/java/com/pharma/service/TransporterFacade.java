package com.pharma.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyperledger.fabric.client.Gateway;
import org.springframework.stereotype.Component;

import static com.pharma.service.Constants.CHANNEL_NAME;

@Component
public class TransporterFacade {

  private final Gateway transporterGateway;
  private final ShipmentService shipmentService;
  private final ObjectMapper objectMapper;

  public TransporterFacade(Gateway transporterGateway) {
    this.transporterGateway = transporterGateway;
    this.shipmentService = ShipmentService.instance(transporterGateway);
    this.objectMapper = new ObjectMapper();
  }
  public void updateShipment(String buyer, String drugName) {
      shipmentService.updateShipment(buyer, drugName);
  }

}

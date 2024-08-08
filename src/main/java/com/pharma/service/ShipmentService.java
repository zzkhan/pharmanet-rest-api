package com.pharma.service;

import com.pharma.rest.model.CreateShipment;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.Gateway;

import static com.pharma.service.Constants.CHAINCODE_NAME;

public class ShipmentService {
  private final Gateway gateway;

  public ShipmentService(Gateway gateway) {
    this.gateway = gateway;
  }

  public static ShipmentService instance(Gateway gateway) {
    return new ShipmentService(gateway);
  }

  public String createShipment(CreateShipment createShipment) {
    Contract contract = gateway.getNetwork(Constants.CHANNEL_NAME).getContract(CHAINCODE_NAME);
    try {
      byte[] result = contract.newProposal("drug-transfer:createDrugShipment")
              .addArguments(createShipment.getBuyer(), createShipment.getDrugName(), createShipment.getTagId(), createShipment.getTransporter())
              .build()
              .endorse()
              .submit();
      return new String(result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void updateShipment(String buyer, String drugName) {
    Contract contract = gateway.getNetwork(Constants.CHANNEL_NAME).getContract(CHAINCODE_NAME);
    try {
      contract.newProposal("drug-transfer:updateDrugShipment")
              .addArguments(buyer, drugName)
              .build()
              .endorse()
              .submit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

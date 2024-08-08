package com.pharma.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharma.rest.model.Challenges;
import com.pharma.rest.model.CreateDrug;
import com.pharma.rest.model.CreatePurchaseOrder;
import com.pharma.rest.model.CreateShipment;
import com.pharma.rest.model.Crp;
import com.pharma.rest.model.DrugVerificationOutcome;
import com.pharma.rest.model.PurchaseOrder;
import org.hyperledger.fabric.client.Gateway;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DistributorFacade {

  private final DrugService drugService;
  private final DrugVerificationService drugVerificationService;
  private final PoService poService;
  private final ShipmentService shipmentService;

  public DistributorFacade(Gateway distributorGateway) {
    drugService = DrugService.instance(distributorGateway);
    poService = PoService.instance(distributorGateway);
    shipmentService = ShipmentService.instance(distributorGateway);
    drugVerificationService = DrugVerificationService.instance(distributorGateway);
  }
  public String getAllDrugs() throws Exception {
      return drugService.getAllDrugs();
  }
  public PurchaseOrder createPurchaseOrder(CreatePurchaseOrder createPurchaseOrder) {
    return poService.createPo(createPurchaseOrder.getSeller(), createPurchaseOrder.getDrugName());
  }
  public void createShipment(CreateShipment createShipment) {
    shipmentService.createShipment(createShipment);
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
}

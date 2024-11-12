package com.pharma.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharma.rest.model.CreateDrug;
import com.pharma.rest.model.CreateShipment;
import com.pharma.rest.model.Crp;
import com.pharma.rest.model.Drug;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.client.Gateway;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ManufacturerFacade {

  private final Gateway manufacturerGateway;
  private final DrugService drugService;
  private final DrugVerificationService drugVerificationService;
  private final ShipmentService shipmentService;

  public Gateway gateway() {
    return manufacturerGateway;
  }

  public ManufacturerFacade(Gateway manufacturerGateway, ObjectMapper objectMapper) {
    this.manufacturerGateway = manufacturerGateway;
    this.drugService = DrugService.instance(manufacturerGateway, objectMapper);
    this.shipmentService = ShipmentService.instance(manufacturerGateway);
    this.drugVerificationService = DrugVerificationService.instance(manufacturerGateway);
  }

  public String getAllDrugs() throws Exception {
    return drugService.getAllDrugs();
  }

  public Drug registerDrug(CreateDrug drug, List<Crp> crps) throws Exception {
    return drugService.registerDrug(drug, crps);
  }

  public String createShipment(CreateShipment createShipment) {
    return shipmentService.createShipment(createShipment);
  }

  public void assignCrps(String drugName, String tagId, String assignee) {
    log.info("assigning CRPs and challenges for drug {}-{} to {}", drugName, tagId, assignee);
    var crpsToAssign = drugService.getUnassignedCrps(drugName, tagId);
    drugService.assignCrps(drugName, tagId, assignee, crpsToAssign);
    drugService.assignChallenges(drugName, tagId, assignee, crpsToAssign);
  }
  public void shareAssignedCrps(String drugName, String tagId, String assignee) {
    log.info("sharing verification CRPs for drug {}-{} to {}", drugName, tagId, assignee);
    List<Crp> assignedCrps = drugService.getAssignedCrps(drugName, tagId, assignee);
    drugVerificationService.shareAssignedCrps(drugName, tagId, assignee, assignedCrps);
  }

  public List<Crp> getAssignedCrps(String drugName, String tagId, String assignee) {
    return drugService.getAssignedCrps(drugName, tagId, assignee);
  }
}

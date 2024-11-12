package com.pharma.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharma.adapter.DrugAdapter;
import com.pharma.adapter.DrugCrpAdapter;
import com.pharma.adapter.DrugSaleLookupAdapter;
import com.pharma.rest.model.CreateDrug;
import com.pharma.rest.model.Crp;
import com.pharma.rest.model.Drug;
import com.pharma.rest.model.DrugSaleLookupResponse;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.CommitStatusException;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.GatewayException;
import org.hyperledger.fabric.client.SubmitException;

import java.io.IOException;
import java.util.List;

@Slf4j
public class DrugService extends BlockChainService {
  private final ObjectMapper objectMapper;
  public DrugService(Gateway gateway, ObjectMapper objectMapper) {
    super(gateway);
    this.objectMapper = objectMapper;
  }
  public static DrugService instance(Gateway gateway, ObjectMapper objectMapper) {
    return new DrugService(gateway, objectMapper);
  }

  public String getAllDrugs() throws Exception {
    byte[] bytes = getContract().evaluateTransaction("pharmanet-queries:ReadAllDrugs");
    return new String(bytes);
  }

  public Drug registerDrug(CreateDrug drug, List<Crp> crps) {
    byte[] bytes = new byte[0];
    try {
      bytes = getContract()
              .newProposal("drug-registration:RegisterDrug")
              .addArguments(objectMapper.writeValueAsString(drug))
              .putTransient("crps", objectMapper.writeValueAsBytes(crps))
              .build()
              .endorse()
              .submit();
    } catch (SubmitException | CommitStatusException | CommitException | EndorseException e) {
      log.info("Error registering drug {}", e.getMessage());
      throw new RuntimeException(e);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return DrugAdapter.fromBytes(bytes);
  }

  public List<Crp> getUnassignedCrps(String drugName, String tagId) {
    try {
      byte[] bytes = getContract().evaluateTransaction("drug-verification:getUnassignedCrps", drugName, tagId);
      return objectMapper.readValue(bytes, new TypeReference<List<Crp>>() {});
    } catch (GatewayException e) {
      throw new RuntimeException(e);
    } catch (StreamReadException e) {
      throw new RuntimeException(e);
    } catch (DatabindException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void assignCrps(String drugName, String tagId, String drugOwner, List<Crp> crpsToAssign) {
    try {
      byte[] bytes = getContract()
              .newProposal("drug-verification:assignCRPs")
              .addArguments(drugName, tagId, drugOwner)
              .putTransient("assignment-crps", objectMapper.writeValueAsBytes(crpsToAssign))
              .build()
              .endorse()
              .submit();
    } catch (SubmitException e) {
      throw new RuntimeException(e);
    } catch (CommitStatusException e) {
      throw new RuntimeException(e);
    } catch (CommitException e) {
      throw new RuntimeException(e);
    } catch (EndorseException e) {
      throw new RuntimeException(e);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public void assignChallenges(String drugName, String tagId, String drugOwner, List<Crp> crpsToAssign) {
    try {
      getContract()
              .newProposal("drug-verification:assignChallenges")
              .addArguments(drugName, tagId, drugOwner)
              .putTransient("assignment-crps", objectMapper.writeValueAsBytes(crpsToAssign))
              .build()
              .endorse()
              .submit();
    } catch (SubmitException | CommitStatusException | CommitException | EndorseException | JsonProcessingException e) {
      log.info("Error assigning challenges {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public List<Crp> getAssignedCrps(String drugName, String tagId, String assignee) {
    try {
      byte[] bytes = getContract().evaluateTransaction("drug-verification:getAssignedCrps", drugName, tagId, assignee);
      return DrugCrpAdapter.fromBytes(bytes);
    } catch (GatewayException e) {
      throw new RuntimeException(e);
    }
  }

  public DrugSaleLookupResponse getByRetailKey(String retailKey) {
    try {
      byte[] bytes = getContract().evaluateTransaction("pharmanet-queries:verifyDrugSale", retailKey);
      return DrugSaleLookupAdapter.fromBytes(bytes);
    } catch (GatewayException e) {
      throw new RuntimeException(e);
    }
  }
}

package com.pharma.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.CommitStatusException;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.SubmitException;

public class RetailService extends BlockChainService {
  private final ObjectMapper objectMapper;
  public RetailService(Gateway gateway) {
    super(gateway);
    this.objectMapper = new ObjectMapper();
  }

  public static RetailService instance(Gateway gateway) {
    return new RetailService(gateway);
  }

  public String retailDrug(String drugName, String drugId) {
    try {
      byte[] retailRecordKey = getContract()
              .newProposal("drug-retail:retailDrug")
              .addArguments(drugName, drugId)
              .setEndorsingOrganizations("Org1MSP","Org4MSP")
              .build()
              .endorse()
              .submit();

      return new String(retailRecordKey);
    } catch (SubmitException e) {
      throw new RuntimeException(e);
    } catch (CommitStatusException e) {
      throw new RuntimeException(e);
    } catch (CommitException e) {
      throw new RuntimeException(e);
    } catch (EndorseException e) {
      throw new RuntimeException(e);
    }
  }
}

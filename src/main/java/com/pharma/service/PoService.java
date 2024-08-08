package com.pharma.service;

import com.pharma.rest.model.PurchaseOrder;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.CommitStatusException;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.SubmitException;

public class PoService extends BlockChainService {
  public PoService(Gateway gateway) {
    super(gateway);
  }
  public static PoService instance(Gateway gateway) {
    return new PoService(gateway);
  }
  public PurchaseOrder createPo(String seller, String drugName) {
    try {
      byte[] result = getContract()
              .newProposal("drug-transfer:createDrugPO")
              .addArguments(seller, drugName)
              .build()
              .endorse()
              .submit();
      return PurchaseOrder.builder().seller(seller).drugName(drugName).build();
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

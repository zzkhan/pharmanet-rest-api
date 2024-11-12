package com.pharma.service;

import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.Gateway;

import static com.pharma.service.Constants.CHAINCODE_NAME;
import static com.pharma.service.Constants.CHANNEL_NAME;

public abstract class BlockChainService {
  protected final Gateway gateway;

  public BlockChainService(Gateway gateway) {
    this.gateway = gateway;
  }

  protected Contract getContract() {
    return gateway.getNetwork(CHANNEL_NAME).getContract(CHAINCODE_NAME);
  }
}

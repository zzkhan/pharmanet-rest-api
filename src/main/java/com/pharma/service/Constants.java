package com.pharma.service;

public class Constants {
  public static final String CHANNEL_NAME = System.getenv().getOrDefault("CHANNEL_NAME", "mychannel");
  public static final String CHAINCODE_NAME = System.getenv().getOrDefault("CHAINCODE_NAME", "drug");
}

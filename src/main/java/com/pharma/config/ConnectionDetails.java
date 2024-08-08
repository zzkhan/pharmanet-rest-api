package com.pharma.config;


import lombok.Data;

@Data
public class ConnectionDetails {

  String mspId;
  String cryptoBasePath;
  String certPath;
  String keystorePath;
  String tlsCertPath;
  String peerEndpoint;
  String overrideAuth;
}

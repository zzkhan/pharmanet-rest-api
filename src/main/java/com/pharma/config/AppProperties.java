package com.pharma.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties("app.fabric-config")
@Data
public class AppProperties {

  @Value("channel-name")
  String channelName;

  @Value("chaincode-name")
  String chaincodeName;

  @NestedConfigurationProperty
  Map<String, ConnectionDetails> connections = new HashMap<>();
}

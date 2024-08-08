package com.pharma.adapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pharma.rest.model.CreateShipment;
import com.pharma.rest.model.Shipment;

import java.io.IOException;

public class DrugShipmentAdapter {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.registerModule(new JavaTimeModule());
  }
  public static String serialise(CreateShipment drugShipment) {
    try {
      return objectMapper.writeValueAsString(drugShipment);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static Shipment fromBytes(byte[] bytes) {
    try {
      return objectMapper.readValue(bytes, Shipment.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

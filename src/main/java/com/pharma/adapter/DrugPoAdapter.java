package com.pharma.adapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pharma.rest.model.PurchaseOrder;

public class DrugPoAdapter {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.registerModule(new JavaTimeModule());
  }
  public static String serialise(PurchaseOrder newDrugPo) {
    try {
      return objectMapper.writeValueAsString(newDrugPo);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static PurchaseOrder deserialise(String json) {
    try {
      return objectMapper.readValue(json, PurchaseOrder.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}

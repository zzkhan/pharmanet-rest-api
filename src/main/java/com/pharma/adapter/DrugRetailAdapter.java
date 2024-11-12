package com.pharma.adapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pharma.rest.model.DrugRetail;

import java.io.IOException;

public class DrugRetailAdapter {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.registerModule(new JavaTimeModule());
  }

  public static DrugRetail fromBytes(byte[] drugSaleBytes) {
    try {
      DrugRetail drugRetail = objectMapper.readValue(drugSaleBytes, new TypeReference<>() {
      });
      return drugRetail;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}

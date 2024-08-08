package com.pharma.adapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pharma.rest.model.DrugVerificationOutcome;

import java.io.IOException;

public class DrugVerificationOutcomeAdapter {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.registerModule(new JavaTimeModule());
  }
  public static DrugVerificationOutcome fromBytes(byte[] bytes) {
    try {
      return objectMapper.readValue(bytes, DrugVerificationOutcome.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

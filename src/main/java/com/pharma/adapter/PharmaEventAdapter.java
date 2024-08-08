package com.pharma.adapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pharma.rest.model.DrugVerificationSubmissionEvent;

import java.io.IOException;

public class PharmaEventAdapter {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.registerModule(new JavaTimeModule());
  }
  public static <T> T fromBytes(byte[] payload, Class<T> drugVerificationSubmissionEventClass) {
    try {
      return objectMapper.readValue(payload, drugVerificationSubmissionEventClass);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

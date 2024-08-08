package com.pharma.adapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharma.rest.model.Crp;

import java.io.IOException;
import java.util.List;

public class DrugCrpAdapter {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public static List<Crp> fromBytes(byte[] drugCrpBytes) {
    try {
      return objectMapper.readValue(drugCrpBytes, new TypeReference<List<Crp>>() {});
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static byte[] toBytes(List<Crp> drugCrpList) {
    try {
      return objectMapper.writeValueAsBytes(drugCrpList);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}

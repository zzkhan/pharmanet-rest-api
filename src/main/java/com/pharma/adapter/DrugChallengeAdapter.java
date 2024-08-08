package com.pharma.adapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharma.rest.model.Challenges;

import java.io.IOException;
import java.util.List;

public class DrugChallengeAdapter {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
  public static List<Challenges> fromBytes(byte[] bytes) {
    try {
      return objectMapper.readValue(bytes, new TypeReference<List<Challenges>>() {});
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

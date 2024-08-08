package com.pharma.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Data
@Jacksonized
@Builder
public class DrugPayload {
  String tagId;

  String drugName;

  String mfgDate;

  @Builder.Default
  private List<Crp> crps = new ArrayList<>();
}

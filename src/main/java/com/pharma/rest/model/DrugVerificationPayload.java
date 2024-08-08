package com.pharma.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class DrugVerificationPayload {
  String drugName;
  String tagId;
  List<Crp> crps;
}

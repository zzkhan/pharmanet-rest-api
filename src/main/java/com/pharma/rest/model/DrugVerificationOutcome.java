package com.pharma.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Data
@Builder
@Jacksonized
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
public class DrugVerificationOutcome {

  @ToString.Include
  String tagId;

  @ToString.Include
  String verifierOrg;

  byte[] assignedCrpsHash;

  byte[] verificationCrpsHash;

  Instant created;

  Instant updated;

  @ToString.Include
  String status;
}

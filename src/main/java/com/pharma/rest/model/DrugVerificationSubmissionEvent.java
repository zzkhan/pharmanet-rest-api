package com.pharma.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class DrugVerificationSubmissionEvent {
  String drugName;
  String tagId;
  String submitter;
}

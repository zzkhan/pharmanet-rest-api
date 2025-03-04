package com.pharma.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Jacksonized
@Builder
public class CreateDrug {
  String tagId;
  String drugName;
  LocalDateTime manufactureDate;
  LocalDateTime expiryDate;
}

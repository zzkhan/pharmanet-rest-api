package com.pharma.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Jacksonized
@Builder
public class DrugPayload {
  String tagId;

  String drugName;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  LocalDateTime manufactureDate;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  LocalDateTime expiryDate;

  @Builder.Default
  private List<Crp> crps = new ArrayList<>();
}

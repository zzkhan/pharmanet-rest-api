package com.pharma.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class CreateShipment {
  String drugName;
  String tagId;
  String buyer;
  String transporter;
}

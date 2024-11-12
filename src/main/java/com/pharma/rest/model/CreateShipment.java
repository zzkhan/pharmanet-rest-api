package com.pharma.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Jacksonized
@Builder
public class CreateShipment {
  String drugName;
  List<String> tagIds;
  String buyer;
  String transporter;
}

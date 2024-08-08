package com.pharma.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@Jacksonized
public class Shipment {
  String id;
  String seller;
  String buyer;
  String transporter;
  String drugName;
  List<String> lineItems;
  String status;
  Instant created;
  Instant updated;
}

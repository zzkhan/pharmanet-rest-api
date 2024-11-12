package com.pharma.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class CreatePurchaseOrder {
  String seller;
  String drugName;
  int quantity;
}

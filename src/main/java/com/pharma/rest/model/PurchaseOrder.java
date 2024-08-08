package com.pharma.rest.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PurchaseOrder {
  String seller;
  String drugName;
}

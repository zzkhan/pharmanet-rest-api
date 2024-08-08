package com.pharma.rest.model;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
public class Crp {
  String challenge;
  String response;
}

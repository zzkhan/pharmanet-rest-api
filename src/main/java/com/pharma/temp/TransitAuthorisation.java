package com.pharma.temp;

import lombok.Value;

@Value
public class TransitAuthorisation implements Authorisation {
  String location;

  @Override
  public String getType() {
    return "TRANSIT";
  }
}

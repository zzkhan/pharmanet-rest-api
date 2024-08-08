package com.pharma.temp;

import org.springframework.stereotype.Component;

public class TransitAuthBuilder implements AuthBuilder {
  @Override
  public TransitAuthorisation build() {
    return new TransitAuthorisation("location");
  }
}

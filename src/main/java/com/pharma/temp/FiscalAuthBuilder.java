package com.pharma.temp;

public class FiscalAuthBuilder implements AuthBuilder {
  @Override
  public FiscalAuthorisation build() {
    return new FiscalAuthorisation();
  }
}

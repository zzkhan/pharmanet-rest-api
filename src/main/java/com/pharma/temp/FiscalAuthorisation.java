package com.pharma.temp;

public class FiscalAuthorisation implements Authorisation {
  @Override
  public String getType() {
    return "FISCAL";
  }
}

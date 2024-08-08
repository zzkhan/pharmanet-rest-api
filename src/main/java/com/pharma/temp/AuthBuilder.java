package com.pharma.temp;

public interface AuthBuilder<T extends Authorisation> {
  T build();
}

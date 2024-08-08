package com.pharma.temp;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AuthBuilderFactory<T extends Authorisation>{

  @Autowired
  private AuthBuilder<T> transitAuthBuilder;
  @Autowired
  private AuthBuilder<T> fiscalAuthBuilder;

  public AuthBuilder<T> getBuilder(String type){
    if("T".equals(type))
      return transitAuthBuilder;
    else
      return fiscalAuthBuilder;
  }

  public static void main(String[] args) {
    AuthBuilderFactory<Authorisation> authBuilderFactory = new AuthBuilderFactory(new TransitAuthBuilder(), new FiscalAuthBuilder());
    AuthBuilder<Authorisation> builder = authBuilderFactory.getBuilder("F");
    Authorisation build = builder.build();
  }
}

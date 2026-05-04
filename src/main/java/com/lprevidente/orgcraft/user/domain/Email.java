package com.lprevidente.orgcraft.user.domain;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

@ValueObject
public record Email(String value) {

  public Email {
    Assert.notNull(value, "hashedValue must not be null");
    Assert.isTrue(value.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "The hashedValue is not a valid");
  }

  @Override
  public String toString() {
    return value;
  }
}

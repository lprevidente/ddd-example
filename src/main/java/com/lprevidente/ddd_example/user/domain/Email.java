package com.lprevidente.ddd_example.user.domain;

import org.springframework.util.Assert;

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

package com.lprevidente.ddd_example.user.domain;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.util.Assert;

public record UserId(UUID id) implements Serializable {

  public UserId {
    Assert.notNull(id, "id must not be null");
  }

  public UserId() {
    this(UUID.randomUUID());
  }
}

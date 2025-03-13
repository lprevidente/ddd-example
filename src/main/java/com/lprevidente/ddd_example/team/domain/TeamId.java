package com.lprevidente.ddd_example.team.domain;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.util.Assert;

public record TeamId(UUID id) implements Serializable {

  public TeamId {
    Assert.notNull(id, "id must not be null");
  }

  public TeamId() {
    this(UUID.randomUUID());
  }
}

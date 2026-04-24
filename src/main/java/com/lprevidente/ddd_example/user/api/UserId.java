package com.lprevidente.ddd_example.user.api;

import com.lprevidente.ddd_example.common.identifier.Identifier;
import java.io.Serializable;
import java.util.UUID;
import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

@ValueObject
public record UserId(UUID id) implements Identifier, Serializable {

  public UserId {
    Assert.notNull(id, "id must not be null");
  }

  public UserId() {
    this(UUID.randomUUID());
  }
}

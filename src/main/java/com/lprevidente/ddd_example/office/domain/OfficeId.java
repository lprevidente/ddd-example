package com.lprevidente.ddd_example.office.domain;

import com.lprevidente.ddd_example.common.identifier.Identifier;
import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.UUID;

@ValueObject
public record OfficeId(UUID id) implements Serializable, Identifier {

  public OfficeId {
    Assert.notNull(id, "id must not be null");
  }

  public OfficeId() {
    this(UUID.randomUUID());
  }
}

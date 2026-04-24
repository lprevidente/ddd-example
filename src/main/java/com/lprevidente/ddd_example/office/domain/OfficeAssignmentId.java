package com.lprevidente.ddd_example.office.domain;

import com.lprevidente.ddd_example.common.identifier.Identifier;
import java.io.Serializable;
import java.util.UUID;
import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

@ValueObject
public record OfficeAssignmentId(UUID id) implements Identifier, Serializable {

  public OfficeAssignmentId {
    Assert.notNull(id, "id must not be null");
  }

  public OfficeAssignmentId() {
    this(UUID.randomUUID());
  }
}

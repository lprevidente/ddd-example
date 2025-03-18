package com.lprevidente.ddd_example.team.domain;

import com.lprevidente.ddd_example.common.identifier.Identifier;
import java.io.Serializable;
import java.util.UUID;
import org.springframework.util.Assert;

public record UserId(UUID id) implements Serializable, Identifier {

  public UserId {
    Assert.notNull(id, "id must not be null");
  }
}

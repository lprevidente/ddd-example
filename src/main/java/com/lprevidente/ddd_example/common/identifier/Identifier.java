package com.lprevidente.ddd_example.common.identifier;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.UUID;

@JsonSerialize(using = IdentifierSerializer.class)
@JsonDeserialize(using = IdentifierDeserializer.class)
public interface Identifier {

  UUID id();
}

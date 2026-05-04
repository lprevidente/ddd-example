package com.lprevidente.orgcraft.common.identifier;

import java.util.UUID;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = IdentifierSerializer.class)
@JsonDeserialize(using = IdentifierDeserializer.class)
public interface Identifier {

  UUID id();
}

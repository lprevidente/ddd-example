package com.lprevidente.orgcraft.common.identifier;

import java.io.IOException;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

class IdentifierSerializer extends ValueSerializer<Identifier> {

  @Override
  public void serialize(
      Identifier identifier, //
      JsonGenerator jsonGenerator,
      SerializationContext ctxt)
      throws JacksonException {

    if (identifier == null) {
      jsonGenerator.writeNull();
      return;
    }

    final var value = identifier.id();
    jsonGenerator.writeString(value.toString());
  }
}

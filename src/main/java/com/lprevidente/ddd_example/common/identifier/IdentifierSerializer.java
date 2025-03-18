package com.lprevidente.ddd_example.common.identifier;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class IdentifierSerializer extends JsonSerializer<Identifier> {

  @Override
  public void serialize(
      Identifier identifier, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {

    if (identifier == null) {
      jsonGenerator.writeNull();
      return;
    }

    final var value = identifier.id();
    jsonGenerator.writeString(value.toString());
  }
}

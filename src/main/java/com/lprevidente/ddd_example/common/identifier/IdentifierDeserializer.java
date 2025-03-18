package com.lprevidente.ddd_example.common.identifier;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import java.io.IOException;
import java.util.UUID;
import lombok.NoArgsConstructor;

@NoArgsConstructor
class IdentifierDeserializer extends JsonDeserializer<Object>
    implements ContextualDeserializer {

  private Class<?> targetClass;

  public IdentifierDeserializer(Class<?> targetClass) {
    this.targetClass = targetClass;
  }

  @Override
  public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    if (targetClass == null) {
      throw new IOException("Target class not set for IdentifierDeserializer");
    }

    String value = p.getValueAsString();
    if (value == null || value.isEmpty()) {
      return null;
    }

    try {
      final var uuid = UUID.fromString(value);
      return targetClass.getConstructor(UUID.class).newInstance(uuid);
    } catch (Exception e) {
      throw new IOException("Failed to deserialize identifier: " + e.getMessage(), e);
    }
  }

  @Override
  public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
    Class<?> targetType = ctxt.getContextualType().getRawClass();
    return new IdentifierDeserializer(targetType);
  }
}

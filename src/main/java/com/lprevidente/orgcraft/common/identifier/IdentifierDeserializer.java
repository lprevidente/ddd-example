package com.lprevidente.orgcraft.common.identifier;

import java.util.UUID;
import lombok.NoArgsConstructor;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

@NoArgsConstructor
class IdentifierDeserializer extends ValueDeserializer<Object> {

  private Class<?> targetClass;

  public IdentifierDeserializer(Class<?> targetClass) {
    this.targetClass = targetClass;
  }

  @Override
  public Object deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
    if (targetClass == null) {
      throw ctxt.instantiationException(Object.class, "Target class not set for IdentifierDeserializer");
    }

    String value = p.getValueAsString();
    if (value == null || value.isEmpty()) {
      return null;
    }

    try {
      final var uuid = UUID.fromString(value);
      return targetClass.getConstructor(UUID.class).newInstance(uuid);
    } catch (Exception e) {
      throw ctxt.instantiationException(targetClass, e);
    }
  }

  @Override
  public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
    Class<?> targetType = ctxt.getContextualType().getRawClass();
    return new IdentifierDeserializer(targetType);
  }
}

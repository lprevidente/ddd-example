package com.lprevidente.ddd_example.common.identifier;

import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.UUID;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

@Slf4j
final class StringToIdentifierConverterFactory<T extends Identifier>
    implements ConverterFactory<String, T> {

  @Override
  @NotNull
  public <T1 extends T> Converter<String, T1> getConverter(Class<T1> targetType) {
    return new StringToIdentifierConverter(targetType);
  }

  private static final class StringToIdentifierConverter<T extends Identifier>
      implements Converter<String, T> {
    private final Class<T> tClass;

    public StringToIdentifierConverter(Class<T> tClass) {
      this.tClass = tClass;
    }

    @SneakyThrows
    public T convert(String source) {
      if (source == null || source.isEmpty()) return null;

      try {
        final var uuid = UUID.fromString(source);
        return tClass.getConstructor(UUID.class).newInstance(uuid);
      } catch (Exception e) {
        throw new IOException("Failed to deserialize identifier: " + e.getMessage(), e);
      }
    }
  }
}

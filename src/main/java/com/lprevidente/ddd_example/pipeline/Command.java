package com.lprevidente.ddd_example.pipeline;

import java.lang.reflect.ParameterizedType;

public interface Command<R> {

  interface Handler<C extends Command<R>, R> {

    R handle(C command);

    @SuppressWarnings("unchecked")
    default Class<C> getClassType() {
      final var genericSuperclass = getClass().getGenericSuperclass();

      // Handle both direct superclass and interface implementations
      if (genericSuperclass instanceof ParameterizedType paramType)
        return (Class<C>) paramType.getActualTypeArguments()[0];

      // Check implemented interfaces if superclass doesn't have the type parameter
      for (final var genericInterface : getClass().getGenericInterfaces()) {
        if (genericInterface instanceof ParameterizedType paramType)
          return (Class<C>) paramType.getActualTypeArguments()[0];
      }

      throw new IllegalStateException(
          "Could not determine the command type for " + getClass().getName());
    }
  }
}

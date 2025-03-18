package com.lprevidente.ddd_example.user.api;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserApi {

  boolean existsById(UUID id);

  <T> Optional<T> findById(UUID id, Class<T> clazz);

  <T extends UserIdDto> Map<UUID, T> findAllById(Collection<UUID> ids, Class<T> clazz);
}

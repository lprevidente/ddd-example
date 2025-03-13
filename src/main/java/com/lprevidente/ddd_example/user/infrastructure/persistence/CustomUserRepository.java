package com.lprevidente.ddd_example.user.infrastructure.persistence;

import com.lprevidente.ddd_example.user.api.BaseInfoView;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomUserRepository {
  <T extends BaseInfoView> List<T> fetchAll(Class<T> clazz);

  <T extends BaseInfoView> List<T> fetchAllById(Collection<UUID> ids, Class<T> clazz);

  <T extends BaseInfoView> Optional<T> fetchById(UUID id, Class<T> clazz);
}

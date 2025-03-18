package com.lprevidente.ddd_example.user.api;

import com.lprevidente.ddd_example.user.domain.*;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserApiImpl implements UserApi {
  private final Users users;

  @Override
  public boolean existsById(UUID id) {
    return users.existsById(new UserId(id));
  }

  @Override
  public <T> Optional<T> findById(UUID id, Class<T> clazz) {
    return users.findById(new UserId(id), clazz);
  }

  @Override
  public <T extends UserIdDto> Map<UUID, T> findAllById(Collection<UUID> ids, Class<T> clazz) {
    return users.findAllByIdIn(ids.stream().map(UserId::new).toList(), clazz).stream()
        .collect(Collectors.toMap(t -> t.getId().id(), Function.identity()));
  }
}

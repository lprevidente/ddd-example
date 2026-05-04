package com.lprevidente.orgcraft.user.api;

import com.lprevidente.orgcraft.user.application.query.UserReadRepository;
import com.lprevidente.orgcraft.user.domain.Users;
import com.lprevidente.orgcraft.user.api.UserId;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
class UserApiImpl implements UserApi {
  private final Users users;
  private final UserReadRepository userReadRepository;

  @Override
  public boolean existsById(UUID id) {
    return users.existsById(new UserId(id));
  }

  @Override
  public <T> Optional<T> findById(UUID id, Class<T> clazz) {
    return userReadRepository.findById(new UserId(id), clazz);
  }

  @Override
  public <T extends UserIdDto> Map<UUID, T> findAllById(Collection<UUID> ids, Class<T> clazz) {
    return userReadRepository.findAllByIdIn(ids.stream().map(UserId::new).toList(), clazz).stream()
        .collect(Collectors.toMap(t -> t.getId().id(), Function.identity()));
  }
}

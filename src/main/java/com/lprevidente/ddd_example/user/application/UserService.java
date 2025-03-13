package com.lprevidente.ddd_example.user.application;

import com.lprevidente.ddd_example.user.api.BaseInfoView;
import com.lprevidente.ddd_example.user.api.UserApi;
import com.lprevidente.ddd_example.user.application.dto.UserRegistrationDTO;
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
public class UserService implements UserApi {
  private final UserRepository userRepository;

  public void createUser(UserRegistrationDTO registrationDTO) {
    final var user =
        new User(
            registrationDTO.firstName(),
            registrationDTO.lastName(),
            Password.create(registrationDTO.password()),
            new Email(registrationDTO.password()),
            userRepository);

    userRepository.save(user);
  }

  @Override
  public <T extends BaseInfoView> Optional<T> findById(UUID id, Class<T> clazz) {
    return userRepository.fetchById(id, clazz);
  }

  @Override
  public <T extends BaseInfoView> Map<UUID, T> findAllById(Collection<UUID> ids, Class<T> clazz) {
    return userRepository.fetchAllById(ids, clazz).stream()
        .collect(Collectors.toMap(u -> u.getId().getId(), Function.identity()));
  }
}

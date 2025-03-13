package com.lprevidente.ddd_example.user.application;

import com.lprevidente.ddd_example.user.domain.UserRepository;
import com.lprevidente.ddd_example.user.infrastructure.persistence.UserView;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {
  private final UserRepository userRepository;

  public List<UserView> findAll() {
    return userRepository.fetchAll(UserView.class);
  }

  public UserView getUserById(UUID id) {
    return userRepository.fetchById(id, UserView.class).orElseThrow();
  }
}

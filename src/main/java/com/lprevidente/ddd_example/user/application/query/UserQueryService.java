package com.lprevidente.ddd_example.user.application.query;

import com.lprevidente.ddd_example.user.application.projection.UserView;
import com.lprevidente.ddd_example.user.domain.UserId;
import com.lprevidente.ddd_example.user.domain.exception.UserNotFoundException;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {
  private final UserReadRepository users;

  public Collection<UserView> findAll() {
    return users.findAllBy(UserView.class);
  }

  public UserView getUserById(UserId userId) {
    return users
        .findById(userId, UserView.class)
        .orElseThrow(() -> new UserNotFoundException(userId));
  }
}

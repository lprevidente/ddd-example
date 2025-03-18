package com.lprevidente.ddd_example.user.application;

import com.lprevidente.ddd_example.user.application.dto.UserInfoDto;
import com.lprevidente.ddd_example.user.domain.UserId;
import com.lprevidente.ddd_example.user.domain.Users;
import com.lprevidente.ddd_example.user.domain.exception.UserNotFoundException;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {
  private final Users users;

  public Collection<UserInfoDto> findAll() {
    return users.findAllBy(UserInfoDto.class);
  }

  public UserInfoDto getUserById(UserId userId) {
    return users
        .findById(userId, UserInfoDto.class)
        .orElseThrow(() -> new UserNotFoundException(userId));
  }
}

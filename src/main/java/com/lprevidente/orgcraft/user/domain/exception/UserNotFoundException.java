package com.lprevidente.orgcraft.user.domain.exception;

import com.lprevidente.orgcraft.common.exception.DomainException;
import com.lprevidente.orgcraft.user.api.UserId;
import org.springframework.http.HttpStatus;

/** Exception thrown when a user cannot be found. */
public class UserNotFoundException extends DomainException {

  public UserNotFoundException(UserId userId) {
    super(
        "User with ID %s not found".formatted(userId.id()),
        "USER_NOT_FOUND",
        HttpStatus.NOT_FOUND,
        "User Not Found");
  }
}

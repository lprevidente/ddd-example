package com.lprevidente.ddd_example.user.domain;

import lombok.Getter;

/**
 * Domain exception thrown when attempting to register a user with an email that is already in use
 * by another user.
 */
@Getter
public class EmailAlreadyInUseException extends RuntimeException {
  private final Email email;

  public EmailAlreadyInUseException(Email email) {
    super("Email already in use: " + email.value());
    this.email = email;
  }
}

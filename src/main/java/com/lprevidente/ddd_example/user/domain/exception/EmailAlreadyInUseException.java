package com.lprevidente.ddd_example.user.domain.exception;

import com.lprevidente.ddd_example.exception.DomainException;
import com.lprevidente.ddd_example.user.domain.Email;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when attempting to use an email address that is already in use by another user.
 */
public class EmailAlreadyInUseException extends DomainException {

  public EmailAlreadyInUseException(Email email) {
    super(
        "Email address '%s' is already in use".formatted(email),
        "EMAIL_ALREADY_IN_USE",
        HttpStatus.CONFLICT,
        "Email Already In Use");
  }

  public EmailAlreadyInUseException(String message, String errorCode) {
    super(message, errorCode, HttpStatus.CONFLICT, "Email Already In Use");
  }
}

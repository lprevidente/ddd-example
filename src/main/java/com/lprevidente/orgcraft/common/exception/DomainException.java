package com.lprevidente.orgcraft.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/** Base exception for domain-specific errors. */
@Getter
public class DomainException extends RuntimeException {

  private final String errorCode;
  private final HttpStatus status;
  private final String title;

  public DomainException(String message, String errorCode, HttpStatus status, String title) {
    super(message);
    this.errorCode = errorCode;
    this.status = status;
    this.title = title;
  }

  public DomainException(
      String message, String errorCode, HttpStatus status, String title, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
    this.status = status;
    this.title = title;
  }
}

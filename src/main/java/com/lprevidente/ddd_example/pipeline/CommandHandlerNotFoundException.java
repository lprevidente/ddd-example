package com.lprevidente.ddd_example.pipeline;

import com.lprevidente.ddd_example.exception.DomainException;
import org.springframework.http.HttpStatus;

class CommandHandlerNotFoundException extends DomainException {

  @SuppressWarnings("rawtypes")
  public CommandHandlerNotFoundException(Command command) {
    super(
        "Cannot find a matching handler for " + command.getClass().getSimpleName() + " command",
        "HANDLER_NOT_FOUND",
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Handler Command Not Found");
  }
}

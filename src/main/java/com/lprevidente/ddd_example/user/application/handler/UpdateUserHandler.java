package com.lprevidente.ddd_example.user.application.handler;

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.user.application.command.UpdateUser;
import com.lprevidente.ddd_example.user.domain.Users;
import com.lprevidente.ddd_example.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateUserHandler implements Command.Handler<UpdateUser, Void> {

  private final Users users;

  @Transactional
  public Void handle(UpdateUser command) {
    final var user =
        users
            .findById(command.id()) //
            .orElseThrow(() -> new UserNotFoundException(command.id()));
    user.updateDetails(command.firstName(), command.lastName());
    return null;
  }
}

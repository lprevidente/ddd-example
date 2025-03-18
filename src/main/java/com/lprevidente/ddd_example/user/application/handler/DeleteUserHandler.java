package com.lprevidente.ddd_example.user.application.handler;

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.user.application.command.DeleteUser;
import com.lprevidente.ddd_example.user.domain.Users;
import com.lprevidente.ddd_example.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUserHandler implements Command.Handler<DeleteUser, Void> {

  private final Users users;

  @Override
  public Void handle(DeleteUser command) {
    final var user =
        users
            .findById(command.id()) //
            .orElseThrow(() -> new UserNotFoundException(command.id()));
    users.delete(user);
    return null;
  }
}

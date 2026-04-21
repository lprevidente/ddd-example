package com.lprevidente.ddd_example.user.application.handler;

import com.lprevidente.ddd_example.user.application.command.DeleteUser;
import com.lprevidente.ddd_example.user.domain.Users;
import com.lprevidente.ddd_example.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserHandler {

  private final Users users;

  @CommandHandler
  public void handle(DeleteUser command) {
    final var user =
        users
            .findById(command.id()) //
            .orElseThrow(() -> new UserNotFoundException(command.id()));
    users.delete(user);
  }
}

package com.lprevidente.orgcraft.user.application.handler;

import com.lprevidente.orgcraft.user.application.command.AddUser;
import com.lprevidente.orgcraft.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.jmolecules.ddd.annotation.Service;
import com.lprevidente.orgcraft.user.api.UserId;

@Service
@RequiredArgsConstructor
public class AddUserHandler {

  private final Users users;

  @CommandHandler
  public UserId handle(AddUser command) {
    final var user =
        new User(
            command.firstName(),
            command.lastName(),
            Password.create(command.password()),
            new Email(command.email()),
            users);
    users.save(user);
    return user.getId();
  }
}

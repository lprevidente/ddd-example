package com.lprevidente.ddd_example.user.application.handler;

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.user.application.command.AddUser;
import com.lprevidente.ddd_example.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddUserHandler implements Command.Handler<AddUser, UserId> {

  private final Users users;

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

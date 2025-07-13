package com.lprevidente.ddd_example.user.application.handler;

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.user.application.command.AddUser;
import com.lprevidente.ddd_example.user.domain.*;
import com.lprevidente.ddd_example.user.domain.exception.EmailAlreadyInUseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AddUserHandler implements Command.Handler<AddUser, UserId> {

  private final Users users;

  @Override
  public UserId handle(AddUser cmd) {

    /* ---------- Value Objects ---------- */
    Password password = Password.create(cmd.password());
    Email    email    = new Email(cmd.email());

    /* ---------- Regra de unicidade ---------- */
    if (users.existsByEmail(email)) {
      throw new EmailAlreadyInUseException(email);      // será mapeada para HTTP 409
    }

    /* ---------- Criação da entidade ---------- */
    User user = User.create(cmd.firstName(), cmd.lastName(), password, email);
    users.save(user);

    return user.getId();
  }
}

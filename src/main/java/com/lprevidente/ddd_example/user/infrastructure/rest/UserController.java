package com.lprevidente.ddd_example.user.infrastructure.rest;

import com.lprevidente.ddd_example.user.application.query.UserQueryService;
import com.lprevidente.ddd_example.user.application.command.AddUser;
import com.lprevidente.ddd_example.user.application.command.DeleteUser;
import com.lprevidente.ddd_example.user.application.command.UpdateUser;
import com.lprevidente.ddd_example.user.application.projection.UserView;
import com.lprevidente.ddd_example.user.application.handler.AddUserHandler;
import com.lprevidente.ddd_example.user.application.handler.DeleteUserHandler;
import com.lprevidente.ddd_example.user.application.handler.UpdateUserHandler;
import com.lprevidente.ddd_example.user.domain.UserId;
import jakarta.validation.Valid;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
class UserController {
  private final UserQueryService userQueryService;
  private final AddUserHandler addUserHandler;
  private final UpdateUserHandler updateUserHandler;
  private final DeleteUserHandler deleteUserHandler;

  @GetMapping
  Collection<UserView> getUsers() {
    return userQueryService.findAll();
  }

  @GetMapping("{id}")
  UserView getUser(@PathVariable UserId id) {
    return userQueryService.getUserById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  UserId createUser(@RequestBody @Valid AddUser command) {
    return addUserHandler.handle(command);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void updateUser(@RequestBody @Valid UpdateUser command) {
    updateUserHandler.handle(command);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deleteUser(@PathVariable UserId id) {
    deleteUserHandler.handle(new DeleteUser(id));
  }
}

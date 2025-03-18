package com.lprevidente.ddd_example.user.infrastructure.rest;

import com.lprevidente.ddd_example.pipeline.Pipeline;
import com.lprevidente.ddd_example.user.application.UserQueryService;
import com.lprevidente.ddd_example.user.application.command.AddUser;
import com.lprevidente.ddd_example.user.application.command.DeleteUser;
import com.lprevidente.ddd_example.user.application.command.UpdateUser;
import com.lprevidente.ddd_example.user.application.dto.UserInfoDto;
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
  private final Pipeline pipeline;
  private final UserQueryService userQueryService;

  @GetMapping
  public Collection<UserInfoDto> getUsers() {
    return userQueryService.findAll();
  }

  @GetMapping("{id}")
  public UserInfoDto getUser(@PathVariable UserId id) {
    return userQueryService.getUserById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserId createUser(@RequestBody @Valid AddUser dto) {
    return pipeline.send(dto);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateUser(@RequestBody @Valid UpdateUser dto) {
    pipeline.send(dto);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable UserId id) {
    pipeline.send(new DeleteUser(id));
  }
}

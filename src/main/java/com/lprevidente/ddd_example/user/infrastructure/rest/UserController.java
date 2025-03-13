package com.lprevidente.ddd_example.user.infrastructure.rest;

import com.lprevidente.ddd_example.user.application.UserQueryService;
import com.lprevidente.ddd_example.user.application.UserService;
import com.lprevidente.ddd_example.user.application.dto.UserRegistrationDTO;
import com.lprevidente.ddd_example.user.infrastructure.persistence.UserView;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  private final UserQueryService userQueryService;

  @GetMapping
  public List<UserView> getUsers() {
    return userQueryService.findAll();
  }

  @GetMapping("{id}")
  public UserView getUser(@PathVariable UUID id) {
    return userQueryService.getUserById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createUser(@RequestBody @Valid UserRegistrationDTO dto) {
    userService.createUser(dto);
  }
}

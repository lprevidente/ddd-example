package com.lprevidente.ddd_example.user.application.command;

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.user.domain.UserId;
import jakarta.validation.constraints.NotNull;

public record DeleteUser(@NotNull UserId id) implements Command<Void> {}

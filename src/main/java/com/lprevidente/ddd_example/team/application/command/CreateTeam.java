package com.lprevidente.ddd_example.team.application.command;

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.team.domain.TeamId;
import jakarta.validation.constraints.NotBlank;

public record CreateTeam(@NotBlank String name) implements Command<TeamId> {}

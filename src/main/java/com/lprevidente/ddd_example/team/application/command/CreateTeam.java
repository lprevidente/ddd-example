package com.lprevidente.ddd_example.team.application.command;

import jakarta.validation.constraints.NotBlank;

public record CreateTeam(@NotBlank String name) {}

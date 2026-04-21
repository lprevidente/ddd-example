package com.lprevidente.ddd_example.team.application.command;

import jakarta.validation.constraints.NotBlank;
import org.jmolecules.architecture.cqrs.Command;

@Command
public record CreateTeam(@NotBlank String name) {}

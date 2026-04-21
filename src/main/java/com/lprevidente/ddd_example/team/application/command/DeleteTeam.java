package com.lprevidente.ddd_example.team.application.command;

import com.lprevidente.ddd_example.team.domain.TeamId;
import jakarta.validation.constraints.NotNull;
import org.jmolecules.architecture.cqrs.Command;

@Command
public record DeleteTeam(@NotNull TeamId id) {}

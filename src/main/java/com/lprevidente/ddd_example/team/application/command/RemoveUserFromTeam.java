package com.lprevidente.ddd_example.team.application.command;

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.team.domain.UserId;
import jakarta.validation.constraints.NotNull;

public record RemoveUserFromTeam(@NotNull TeamId teamId, @NotNull UserId userId)
    implements Command<Void> {}

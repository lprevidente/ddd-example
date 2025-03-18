package com.lprevidente.ddd_example.team.application.handler;

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.team.application.command.CreateTeam;
import com.lprevidente.ddd_example.team.domain.Team;
import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.team.domain.Teams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CreateTeamHandler implements Command.Handler<CreateTeam, TeamId> {
  private final Teams teams;

  @Override
  public TeamId handle(CreateTeam command) {
    final var team = new Team(command.name());
    teams.save(team);
    return team.getId();
  }
}

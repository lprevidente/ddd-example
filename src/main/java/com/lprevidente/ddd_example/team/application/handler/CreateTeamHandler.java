package com.lprevidente.ddd_example.team.application.handler;

import com.lprevidente.ddd_example.team.application.command.CreateTeam;
import com.lprevidente.ddd_example.team.domain.Team;
import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.team.domain.Teams;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class CreateTeamHandler {
  private final Teams teams;

  @CommandHandler
  public TeamId handle(CreateTeam command) {
    final var team = new Team(command.name());
    teams.save(team);
    return team.getId();
  }
}

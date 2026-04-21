package com.lprevidente.ddd_example.team.application.handler;

import com.lprevidente.ddd_example.team.application.command.DeleteTeam;
import com.lprevidente.ddd_example.team.domain.Teams;
import com.lprevidente.ddd_example.team.domain.exception.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class DeleteTeamHandler {
  private final Teams teams;

  @CommandHandler
  public void handle(DeleteTeam command) {
    final var team =
        teams
            .findById(command.id()) //
            .orElseThrow(() -> new TeamNotFoundException(command.id()));
    teams.delete(team);
  }
}

package com.lprevidente.ddd_example.team.application.handler;

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.team.application.command.DeleteTeam;
import com.lprevidente.ddd_example.team.domain.Teams;
import com.lprevidente.ddd_example.team.domain.exception.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTeamHandler implements Command.Handler<DeleteTeam, Void> {
  private final Teams teams;

  @Override
  public Void handle(DeleteTeam command) {
    final var team =
        teams
            .findById(command.id()) //
            .orElseThrow(() -> new TeamNotFoundException(command.id()));
    teams.delete(team);
    return null;
  }
}

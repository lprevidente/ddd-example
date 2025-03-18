package com.lprevidente.ddd_example.team.application.handler;

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.team.application.command.AddUserToTeam;
import com.lprevidente.ddd_example.team.domain.*;
import com.lprevidente.ddd_example.user.api.UserApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AddUserToTeamHandler implements Command.Handler<AddUserToTeam, Void> {
  private final TeamMembers teamMembers;
  private final UserApi users;
  private final Teams teams;

  @Override
  public Void handle(AddUserToTeam command) {
    final var teamId = new TeamId(command.teamId());
    final var userId = new UserId(command.userId());

    final var membership = new TeamMember(teamId, userId, teams, users, teamMembers);
    teamMembers.save(membership);
    return null;
  }
}

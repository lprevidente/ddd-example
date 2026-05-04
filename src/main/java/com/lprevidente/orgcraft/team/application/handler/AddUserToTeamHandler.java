package com.lprevidente.orgcraft.team.application.handler;

import com.lprevidente.orgcraft.team.application.command.AddUserToTeam;
import com.lprevidente.orgcraft.team.domain.*;
import com.lprevidente.orgcraft.user.api.UserApi;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.jmolecules.ddd.annotation.Service;
import com.lprevidente.orgcraft.user.api.UserId;

@Service
@RequiredArgsConstructor
public class AddUserToTeamHandler {
  private final TeamMembers teamMembers;
  private final UserApi users;
  private final Teams teams;

  @CommandHandler
  public void handle(AddUserToTeam command) {
    final var teamId = new TeamId(command.teamId());
    final var userId = new UserId(command.userId());

    final var membership = new TeamMember(teamId, userId, teams, users, teamMembers);
    teamMembers.save(membership);
  }
}

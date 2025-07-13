package com.lprevidente.ddd_example.team.application.handler;

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.team.application.command.AddUserToTeam;
import com.lprevidente.ddd_example.team.domain.*;
import com.lprevidente.ddd_example.user.api.UserApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
class AddUserToTeamHandler implements Command.Handler<AddUserToTeam, Void> {

  private final TeamMembers teamMembers;
  private final UserApi     users;
  private final Teams       teams;

  @Override
  public Void handle(AddUserToTeam command) {

    /* ---------- Cria os value objects ---------- */
    TeamId teamId = new TeamId(command.teamId());
    UserId userId = new UserId(command.userId());

    /* ---------- Validações de domínio ---------- */
    Assert.isTrue(teams.existsById(teamId),           "team does not exist");
    Assert.isTrue(users.existsById(userId.id()),      "user does not exist");
    Assert.isTrue(!teamMembers.existsById(new TeamMemberId(teamId, userId)),
                  "user is already a member of this team");

    /* ---------- Criação do membro ---------- */
    TeamMember membership = TeamMember.of(teamId, userId);

    teamMembers.save(membership);
    return null;
  }
}

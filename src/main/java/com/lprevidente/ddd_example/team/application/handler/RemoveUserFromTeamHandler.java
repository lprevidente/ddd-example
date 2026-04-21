package com.lprevidente.ddd_example.team.application.handler;

import com.lprevidente.ddd_example.team.application.command.RemoveUserFromTeam;
import com.lprevidente.ddd_example.team.domain.*;
import com.lprevidente.ddd_example.team.domain.exception.TeamMemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class RemoveUserFromTeamHandler {
  private final TeamMembers teamMembers;

  @CommandHandler
  public void handle(RemoveUserFromTeam command) {
    final var membershipId = new TeamMemberId(command.teamId(), command.userId());
    final var teamMember =
        teamMembers
            .findById(membershipId)
            .orElseThrow(() -> new TeamMemberNotFoundException(membershipId));
    teamMembers.delete(teamMember);
  }
}

package com.lprevidente.ddd_example.team.application.handler;

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.team.application.command.RemoveUserFromTeam;
import com.lprevidente.ddd_example.team.domain.*;
import com.lprevidente.ddd_example.team.domain.exception.TeamMemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveUserFromTeamHandler implements Command.Handler<RemoveUserFromTeam, Void> {
  private final TeamMembers teamMembers;

  @Override
  public Void handle(RemoveUserFromTeam command) {
    final var membershipId = new TeamMemberId(command.teamId(), command.userId());
    final var teamMember =
        teamMembers
            .findById(membershipId)
            .orElseThrow(() -> new TeamMemberNotFoundException(membershipId));
    teamMembers.delete(teamMember);
    return null;
  }
}

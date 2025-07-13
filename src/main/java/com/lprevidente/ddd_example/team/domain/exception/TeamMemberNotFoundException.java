package com.lprevidente.ddd_example.team.domain.exception;

import com.lprevidente.ddd_example.exception.DomainException;
import com.lprevidente.ddd_example.team.domain.TeamMemberId;
import org.springframework.http.HttpStatus;

/** Lançada quando não é possível localizar um {@link TeamMemberId} (combinação de Team + User). */
public class TeamMemberNotFoundException extends DomainException {

  public TeamMemberNotFoundException(TeamMemberId id) {
    super(buildMessage(id),
          "TEAM_MEMBER_NOT_FOUND",
          HttpStatus.NOT_FOUND,
          "Team Member Not Found");
  }

  /* -------------------- Helpers -------------------- */

  private static String buildMessage(TeamMemberId id) {
    return "Team member with TeamId %s and UserId %s not found"
        .formatted(id.getTeamId().id(), id.getUserId().id());
  }
}

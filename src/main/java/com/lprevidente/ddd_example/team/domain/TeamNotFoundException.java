package com.lprevidente.ddd_example.team.domain;

import lombok.Getter;

@Getter
public class TeamNotFoundException extends RuntimeException {
  private final TeamId teamId;

  public TeamNotFoundException(TeamId teamId) {
    super("Team not found: " + teamId.id());
    this.teamId = teamId;
  }
}

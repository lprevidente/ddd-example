package com.lprevidente.ddd_example.team.domain.exception;

import com.lprevidente.ddd_example.exception.DomainException;
import com.lprevidente.ddd_example.team.domain.TeamId;
import org.springframework.http.HttpStatus;

/** Exception thrown when a user cannot be found. */
public class TeamNotFoundException extends DomainException {

  public TeamNotFoundException(TeamId teamId) {
    super(
        "Team with ID %s not found".formatted(teamId.id()),
        "TEAM_NOT_FOUND",
        HttpStatus.NOT_FOUND,
        "Team Not Found");
  }
}

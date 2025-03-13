package com.lprevidente.ddd_example.team.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Composite ID for TeamMembership consisting of teamId and userId */
@Embeddable
@Getter
@NoArgsConstructor
public class TeamMemberId implements Serializable {

  @Embedded
  @AttributeOverride(name = "id", column = @Column(name = "team_id"))
  private TeamId teamId;

  @Embedded
  @AttributeOverride(name = "id", column = @Column(name = "user_id"))
  private UserId userId;

  public TeamMemberId(TeamId teamId, UserId userId) {
    this.teamId = teamId;
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TeamMemberId that = (TeamMemberId) o;
    return Objects.equals(teamId, that.teamId) && Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(teamId, userId);
  }
}

package com.lprevidente.ddd_example.team.domain;

import com.lprevidente.ddd_example.user.api.UserApi;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.util.Assert;

@Getter
@Entity
@Table(name = "team_members")
public class TeamMember extends AbstractAggregateRoot<TeamMember> {

  @EmbeddedId private TeamMemberId id;

  private LocalDateTime joinedAt;

  protected TeamMember() {}

  public TeamMember(
      TeamId teamId, UserId userId, Teams teams, UserApi users, TeamMembers teamMembers) {
    Assert.notNull(teamId, "teamId must not be null");
    Assert.notNull(userId, "userId must not be null");

    Assert.isTrue(teams.existsById(teamId), "team does not exist");
    Assert.isTrue(users.existsById(userId.id()), "user does not exist");

    this.id = new TeamMemberId(teamId, userId);

    Assert.isTrue(!teamMembers.existsById(id), "User is already a member of this team");

    this.joinedAt = LocalDateTime.now();
  }

  public TeamId getTeamId() {
    return id.getTeamId();
  }

  public UserId getUserId() {
    return id.getUserId();
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof TeamMember that)) return false;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}

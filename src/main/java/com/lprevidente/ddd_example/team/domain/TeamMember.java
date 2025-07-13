package com.lprevidente.ddd_example.team.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Entity
@Table(name = "team_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)          // JPA
public class TeamMember {

  @EmbeddedId
  private TeamMemberId id;

  @Column(nullable = false, updatable = false)             // imutável
  private final LocalDateTime joinedAt = LocalDateTime.now();

  /* ---------- Fábrica estática ---------- */
  public static TeamMember of(TeamId teamId, UserId userId) {
    return new TeamMember(teamId, userId);
  }

  /* ---------- Construtor privado ---------- */
  private TeamMember(TeamId teamId, UserId userId) {
    setIds(teamId, userId);                                // validação única
  }

  /* ---------- Comportamento utilitário ---------- */
  public TeamId teamId()   { return id.getTeamId(); }
  public UserId userId()   { return id.getUserId(); }

  /* ---------- Igualdade baseada no identificador ---------- */
  @Override
  public final boolean equals(Object o) {
    return o instanceof TeamMember that && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() { return Objects.hashCode(id); }

  /* ---------- Validação interna ---------- */
  private void setIds(TeamId teamId, UserId userId) {
    Assert.notNull(teamId, "teamId must not be null");
    Assert.notNull(userId, "userId must not be null");
    this.id = new TeamMemberId(teamId, userId);
  }
}

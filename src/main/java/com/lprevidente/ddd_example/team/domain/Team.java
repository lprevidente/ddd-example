package com.lprevidente.ddd_example.team.domain;

import com.lprevidente.ddd_example.team.domain.event.TeamCreated;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.util.Assert;

@Entity
@Getter
@Table(name = "teams")
public class Team extends AbstractAggregateRoot<Team> {
  @EmbeddedId private TeamId id;

  private String name;
  private LocalDateTime createdAt;

  protected Team() {}

  public Team(String name) {
    Assert.hasText(name, "name must not be null or empty");

    this.id = new TeamId();
    this.name = name;
    this.createdAt = LocalDateTime.now();

    // Register domain event (could be used for notifications, analytics, etc.)
    registerEvent(new TeamCreated(this));
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Team that)) return false;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}

package com.lprevidente.ddd_example.team.domain;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import org.junit.jupiter.api.Test;

class TeamTest {

  @Test
  void shouldCreateTeamWithValidName() {
    Team team = Team.create("Falcons");

    assertNotNull(team.getId());
    assertEquals("Falcons", team.getName());
    assertTrue(Duration.between(team.getCreatedAt(), java.time.LocalDateTime.now()).toSeconds() < 2);
    assertFalse(team.domainEvents().isEmpty(), "TeamCreated event should be registered");
  }

  @Test
  void shouldFailWhenNameIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> Team.create(" "));
  }

  @Test
  void shouldRenameTeam() {
    Team team = Team.create("Falcons");
    team.renameTo("Eagles");
    assertEquals("Eagles", team.getName());
  }

  @Test
  void createdAtMustRemainImmutable() {
    Team team = Team.create("Falcons");
    var createdAtBefore = team.getCreatedAt();
    team.renameTo("Eagles");
    assertEquals(createdAtBefore, team.getCreatedAt());
  }

  @Test
  void equalityDependsOnlyOnId() {
    Team t1 = Team.create("A");
    Team t2 = Team.create("B");

    // Copiando ID por reflexão apenas para teste
    var idField = Team.class.getDeclaredField("id");
    idField.setAccessible(true);
    idField.set(t2, t1.getId());

    assertEquals(t1, t2);
    assertEquals(t1.hashCode(), t2.hashCode());
  }
}

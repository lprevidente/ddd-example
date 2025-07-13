package com.lprevidente.ddd_example.team.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TeamNameTest {

  @Test
  void shouldCreateValidName() {
    TeamName name = new TeamName(" Time XPTO ");
    assertThat(name.getValue()).isEqualTo("Time XPTO");
  }

  @Test
  void shouldRejectBlankOrNull() {
    assertThatThrownBy(() -> new TeamName(null))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Team name must not be empty");

    assertThatThrownBy(() -> new TeamName("   "))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Team name must not be empty");
  }

  @Test
  void equalsAndHashCodeShouldWork() {
    TeamName a = new TeamName("ABC");
    TeamName b = new TeamName("ABC");
    TeamName c = new TeamName("DEF");

    assertThat(a).isEqualTo(b);
    assertThat(a.hashCode()).isEqualTo(b.hashCode());
    assertThat(a).isNotEqualTo(c);
  }
}

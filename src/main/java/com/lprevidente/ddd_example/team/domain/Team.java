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
 /** Identificador técnico do time (gera-se automaticamente). */
  @EmbeddedId
  private final TeamId id = new TeamId();

  /** Nome de exibição do time – nunca vazio. */
  @Column(nullable = false)
  private String name;

  /** Data/hora de criação (imutável). */
  @Column(nullable = false, updatable = false)
  private final LocalDateTime createdAt = LocalDateTime.now();

  protected Team() {}

  public Team(String name) {
    setName(name);           // encapsula validação
    registerEvent(new TeamCreated(this));
  }
  /** Fábrica estática para criar a entidade de forma expressiva. */
  public static Team create(String name) {
    return new Team(name);
  }

  /** Comportamento de domínio: altera o nome garantindo invariantes. */
  public void renameTo(String newName) {
    setName(newName);
  }

  /** Valida e atribui o nome (evita duplicar lógica). */
  private void setName(String name) {
    Assert.hasText(name, "name must not be null or empty");
    this.name = name.trim();
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

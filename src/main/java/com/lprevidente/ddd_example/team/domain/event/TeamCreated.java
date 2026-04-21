package com.lprevidente.ddd_example.team.domain.event;

import com.lprevidente.ddd_example.team.domain.Team;
import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
public record TeamCreated(Team team) {}

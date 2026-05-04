package com.lprevidente.orgcraft.team.domain.event;

import com.lprevidente.orgcraft.team.domain.Team;
import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
public record TeamCreated(Team team) {}

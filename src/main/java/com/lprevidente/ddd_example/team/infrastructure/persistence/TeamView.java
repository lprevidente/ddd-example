package com.lprevidente.ddd_example.team.infrastructure.persistence;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.lprevidente.ddd_example.team.domain.Team;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EntityView(Team.class)
public abstract class TeamView {

  @IdMapping
  @JsonUnwrapped
  abstract TeamViewId getId();

  abstract String getName();
}

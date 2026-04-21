package com.lprevidente.ddd_example.team.application.projection;

import com.lprevidente.ddd_example.team.domain.TeamId;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface TeamView {
  TeamId getId();

  String getName();
}

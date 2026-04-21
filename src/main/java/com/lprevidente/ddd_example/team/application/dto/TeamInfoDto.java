package com.lprevidente.ddd_example.team.application.dto;

import com.lprevidente.ddd_example.team.domain.TeamId;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface TeamInfoDto {

  TeamId getId();

  String getName();
}

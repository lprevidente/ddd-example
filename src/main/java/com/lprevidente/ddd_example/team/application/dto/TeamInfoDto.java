package com.lprevidente.ddd_example.team.application.dto;

import com.lprevidente.ddd_example.team.domain.TeamId;

public interface TeamInfoDto {

  TeamId getId();

  String getName();
}

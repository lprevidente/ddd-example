package com.lprevidente.ddd_example.team.application;

import com.lprevidente.ddd_example.team.application.dto.TeamInfoDto;
import com.lprevidente.ddd_example.team.domain.Teams;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamQueryService {
  private final Teams teams;

  public Collection<TeamInfoDto> getTeams() {
    return teams.findAllBy(TeamInfoDto.class);
  }
}

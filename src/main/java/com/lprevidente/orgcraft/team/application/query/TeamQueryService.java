package com.lprevidente.orgcraft.team.application.query;

import com.lprevidente.orgcraft.team.application.projection.TeamView;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class TeamQueryService {
  private final TeamReadRepository teams;

  public Collection<TeamView> getTeams() {
    return teams.findAllBy(TeamView.class);
  }
}

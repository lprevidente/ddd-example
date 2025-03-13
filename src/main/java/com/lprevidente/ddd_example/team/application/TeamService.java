package com.lprevidente.ddd_example.team.application;

import com.lprevidente.ddd_example.team.application.command.CreateTeam;
import com.lprevidente.ddd_example.team.domain.Team;
import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.team.domain.TeamNotFoundException;
import com.lprevidente.ddd_example.team.domain.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;

  @Transactional
  public TeamId createTeam(CreateTeam request) {
    final var team = new Team(request.name());
    teamRepository.save(team);
    return team.getId();
  }

  public void deleteTeam(TeamId id) {
    teamRepository.deleteById(id);
  }

  public Team getTeam(TeamId id) {
    return teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
  }
}

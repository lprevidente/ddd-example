package com.lprevidente.ddd_example.team.application;

import com.lprevidente.ddd_example.team.infrastructure.persistence.TeamView;
import com.lprevidente.ddd_example.team.infrastructure.persistence.TeamViewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamQueryService {
  private final TeamViewRepository teamViewRepository;

  public List<TeamView> getAllTeams() {
    return teamViewRepository.findAll();
  }
}

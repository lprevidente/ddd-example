package com.lprevidente.ddd_example.team.infrastructure.rest;

import com.lprevidente.ddd_example.team.application.TeamQueryService;
import com.lprevidente.ddd_example.team.application.TeamService;
import com.lprevidente.ddd_example.team.application.command.CreateTeam;
import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.team.infrastructure.persistence.TeamView;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

  private final TeamService teamService;
  private final TeamQueryService teamQueryService;

  @GetMapping()
  public List<TeamView> getTeams() {
    return teamQueryService.getAllTeams();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TeamId createTeam(@RequestBody @Valid CreateTeam createRequest) {
    return teamService.createTeam(createRequest);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTeam(@PathVariable UUID id) {
    teamService.deleteTeam(new TeamId(id));
  }
}

package com.lprevidente.ddd_example.team.infrastructure.rest;

import com.lprevidente.ddd_example.team.application.query.TeamQueryService;
import com.lprevidente.ddd_example.team.application.command.CreateTeam;
import com.lprevidente.ddd_example.team.application.command.DeleteTeam;
import com.lprevidente.ddd_example.team.application.projection.TeamView;
import com.lprevidente.ddd_example.team.application.handler.CreateTeamHandler;
import com.lprevidente.ddd_example.team.application.handler.DeleteTeamHandler;
import com.lprevidente.ddd_example.team.domain.TeamId;
import jakarta.validation.Valid;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
class TeamController {

  private final TeamQueryService teamQueryService;
  private final CreateTeamHandler createTeamHandler;
  private final DeleteTeamHandler deleteTeamHandler;

  @GetMapping
  Collection<TeamView> getTeams() {
    return teamQueryService.getTeams();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  TeamId createTeam(@RequestBody @Valid CreateTeam command) {
    return createTeamHandler.handle(command);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deleteTeam(@PathVariable @Valid TeamId id) {
    deleteTeamHandler.handle(new DeleteTeam(id));
  }
}

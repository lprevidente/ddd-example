package com.lprevidente.ddd_example.team.infrastructure;

import com.lprevidente.ddd_example.pipeline.Pipeline;
import com.lprevidente.ddd_example.team.application.TeamQueryService;
import com.lprevidente.ddd_example.team.application.command.CreateTeam;
import com.lprevidente.ddd_example.team.application.command.DeleteTeam;
import com.lprevidente.ddd_example.team.application.dto.TeamInfoDto;
import com.lprevidente.ddd_example.team.domain.TeamId;
import jakarta.validation.Valid;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

  private final Pipeline pipeline;
  private final TeamQueryService teamQueryService;

  @GetMapping
  public Collection<TeamInfoDto> getTeams() {
    return teamQueryService.getTeams();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TeamId createTeam(@RequestBody @Valid CreateTeam command) {
    return pipeline.send(command);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTeam(@PathVariable @Valid TeamId id) {
    pipeline.send(new DeleteTeam(id));
  }
}

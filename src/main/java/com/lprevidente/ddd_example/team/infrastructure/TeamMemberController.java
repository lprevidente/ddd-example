package com.lprevidente.ddd_example.team.infrastructure;

import com.lprevidente.ddd_example.pipeline.Pipeline;
import com.lprevidente.ddd_example.team.application.TeamMemberQueryService;
import com.lprevidente.ddd_example.team.application.command.AddUserToTeam;
import com.lprevidente.ddd_example.team.application.command.RemoveUserFromTeam;
import com.lprevidente.ddd_example.team.application.dto.TeamMemberDto;
import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.team.domain.UserId;
import jakarta.validation.Valid;
import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams/{teamId}/members")
@RequiredArgsConstructor
public class TeamMemberController {

  private final Pipeline pipeline;
  private final TeamMemberQueryService teamMemberQueryService;

  @GetMapping
  public Collection<TeamMemberDto> getTeamMembers(@PathVariable TeamId teamId) {
    return teamMemberQueryService.getTeamMembers(teamId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addMember(@RequestBody @Valid AddUserToTeam command) {
    pipeline.send(command);
  }

  @DeleteMapping("{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeMemberFromTeam(@PathVariable TeamId teamId, @PathVariable UserId userId) {
    pipeline.send(new RemoveUserFromTeam(teamId, userId));
  }
}

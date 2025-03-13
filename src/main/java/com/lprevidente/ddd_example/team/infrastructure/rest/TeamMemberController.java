package com.lprevidente.ddd_example.team.infrastructure.rest;

import com.lprevidente.ddd_example.team.application.TeamMemberQueryService;
import com.lprevidente.ddd_example.team.application.TeamMemberService;
import com.lprevidente.ddd_example.team.application.command.AddUserToTeam;
import com.lprevidente.ddd_example.team.application.command.RemoveUserFromTeam;
import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.team.infrastructure.persistence.TeamMemberView;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams/{teamId}/members")
@RequiredArgsConstructor
public class TeamMemberController {

  private final TeamMemberService teamMemberService;
  private final TeamMemberQueryService teamMemberQueryService;

  @GetMapping
  public List<TeamMemberView> getTeamMembers(@PathVariable UUID teamId) {
    return teamMemberQueryService.getTeamMembers(new TeamId(teamId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addMember(@RequestBody @Valid AddUserToTeam command) {
    teamMemberService.addUserToTeam(command);
  }

  @DeleteMapping("{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeMemberFromTeam(@PathVariable UUID teamId, @PathVariable UUID userId) {
    teamMemberService.removeUserFromTeam(new RemoveUserFromTeam(teamId, userId));
  }
}

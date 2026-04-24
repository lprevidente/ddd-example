package com.lprevidente.ddd_example.team.infrastructure.rest;

import com.lprevidente.ddd_example.team.application.query.TeamMemberQueryService;
import com.lprevidente.ddd_example.team.application.command.AddUserToTeam;
import com.lprevidente.ddd_example.team.application.command.RemoveUserFromTeam;
import com.lprevidente.ddd_example.team.application.projection.TeamMemberView;
import com.lprevidente.ddd_example.team.application.handler.AddUserToTeamHandler;
import com.lprevidente.ddd_example.team.application.handler.RemoveUserFromTeamHandler;
import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.user.api.UserId;
import jakarta.validation.Valid;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams/{teamId}/members")
@RequiredArgsConstructor
class TeamMemberController {

  private final TeamMemberQueryService teamMemberQueryService;
  private final AddUserToTeamHandler addUserToTeamHandler;
  private final RemoveUserFromTeamHandler removeUserFromTeamHandler;

  @GetMapping
  Collection<TeamMemberView> getTeamMembers(@PathVariable TeamId teamId) {
    return teamMemberQueryService.getTeamMembers(teamId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  void addMember(@RequestBody @Valid AddUserToTeam command) {
    addUserToTeamHandler.handle(command);
  }

  @DeleteMapping("{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void removeMemberFromTeam(@PathVariable TeamId teamId, @PathVariable UserId userId) {
    removeUserFromTeamHandler.handle(new RemoveUserFromTeam(teamId, userId));
  }
}

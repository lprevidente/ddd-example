package com.lprevidente.ddd_example.team.application;

import com.lprevidente.ddd_example.team.application.command.AddUserToTeam;
import com.lprevidente.ddd_example.team.application.command.RemoveUserFromTeam;
import com.lprevidente.ddd_example.team.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamMemberService {
  private final TeamMemberRepository teamMemberRepository;

  @Transactional
  public void addUserToTeam(AddUserToTeam command) {
    final var teamId = new TeamId(command.teamId());
    final var userId = new UserId(command.userId());

    final var membership = new TeamMember(teamId, userId, teamMemberRepository);
    teamMemberRepository.save(membership);
  }

  @Transactional
  public void removeUserFromTeam(RemoveUserFromTeam command) {
    final var teamId = new TeamId(command.teamId());
    final var userId = new UserId(command.userId());
    final var membershipId = new TeamMemberId(teamId, userId);

    teamMemberRepository.findById(membershipId).ifPresent(teamMemberRepository::delete);
  }
}

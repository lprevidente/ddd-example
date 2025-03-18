package com.lprevidente.ddd_example.team.application;

import com.lprevidente.ddd_example.team.application.dto.MemberDto;
import com.lprevidente.ddd_example.team.application.dto.TeamMemberDto;
import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.team.domain.TeamMemberId;
import com.lprevidente.ddd_example.team.domain.TeamMembers;
import com.lprevidente.ddd_example.team.domain.UserId;
import com.lprevidente.ddd_example.user.api.UserApi;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamMemberQueryService {
  private final UserApi userApi;
  private final TeamMembers teamMembers;

  @Transactional(readOnly = true)
  public Collection<TeamMemberDto> getTeamMembers(TeamId teamId) {
    final var memberships = teamMembers.findAllById_TeamId(teamId, TeamMemberDto.class);

    // Extract all user IDs to fetch user details
    final var userIds =
        memberships.stream() //
            .map(TeamMemberDto::getId)
            .map(TeamMemberId::getUserId)
            .map(UserId::id)
            .collect(Collectors.toSet());

    // Get user details for all members at once
    final var users = userApi.findAllById(userIds, MemberDto.class);

    // Enrich the views with user and team details
    memberships.forEach(
        membership -> {
          final var userId = membership.getId().getUserId().id();
          // if (users.containsKey(userId)) membership.setUser(users.get(userId));
        });

    return memberships;
  }
}

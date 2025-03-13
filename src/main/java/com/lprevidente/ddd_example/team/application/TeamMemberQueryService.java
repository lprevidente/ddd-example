package com.lprevidente.ddd_example.team.application;

import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.team.infrastructure.persistence.*;
import com.lprevidente.ddd_example.user.api.UserApi;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamMemberQueryService {
  private final TeamMemberViewRepository teamMemberViewRepository;
  private final UserApi userApi;

  @Transactional(readOnly = true)
  public List<TeamMemberView> getTeamMembers(TeamId teamId) {
    final var memberships = teamMemberViewRepository.findById_TeamId(teamId);

    // Extract all user IDs to fetch user details
    final var userIds =
        memberships.stream() //
            .map(TeamMemberView::getId)
            .map(TeamMemberViewId::getUserId)
            .map(TeamMemberViewId.UserIdView::getId)
            .collect(Collectors.toSet());

    // Get user details for all members at once
    final var users = userApi.findAllById(userIds, MemberInfoView.class);

    // Enrich the views with user and team details
    memberships.forEach(
        membership -> {
          final var userId = membership.getId().getUserId().getId();
          if (users.containsKey(userId)) membership.setUser(users.get(userId));
        });

    return memberships;
  }
}

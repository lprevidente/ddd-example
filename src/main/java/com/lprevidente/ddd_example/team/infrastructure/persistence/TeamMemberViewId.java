package com.lprevidente.ddd_example.team.infrastructure.persistence;

import com.blazebit.persistence.view.EntityView;
import com.lprevidente.ddd_example.team.domain.TeamMemberId;
import com.lprevidente.ddd_example.team.domain.UserId;
import java.util.UUID;

@EntityView(TeamMemberId.class)
public interface TeamMemberViewId {
  UserIdView getUserId();

  @EntityView(UserId.class)
  interface UserIdView {
    UUID getId();
  }
}

package com.lprevidente.ddd_example.team.infrastructure.persistence;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lprevidente.ddd_example.team.domain.TeamMember;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EntityView(TeamMember.class)
public abstract class TeamMemberView {

  private MemberInfoView user;

  @IdMapping
  @JsonIgnore
  public abstract TeamMemberViewId getId();

  abstract LocalDateTime getJoinedAt();
}

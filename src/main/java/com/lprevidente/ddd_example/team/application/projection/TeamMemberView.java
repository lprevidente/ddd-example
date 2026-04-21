package com.lprevidente.ddd_example.team.application.projection;

import com.lprevidente.ddd_example.team.domain.TeamMemberId;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface TeamMemberView {
  TeamMemberId getId();
}

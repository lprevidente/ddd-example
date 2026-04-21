package com.lprevidente.ddd_example.team.application.dto;

import com.lprevidente.ddd_example.team.domain.TeamMemberId;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface TeamMemberDto {

  TeamMemberId getId();
}

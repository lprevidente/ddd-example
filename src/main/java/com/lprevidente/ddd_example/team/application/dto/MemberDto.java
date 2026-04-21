package com.lprevidente.ddd_example.team.application.dto;

import com.lprevidente.ddd_example.user.api.UserIdDto;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface MemberDto extends UserIdDto {

  String getFirstName();

  String getLastName();
}

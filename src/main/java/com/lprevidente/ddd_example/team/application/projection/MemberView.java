package com.lprevidente.ddd_example.team.application.projection;

import com.lprevidente.ddd_example.user.api.UserIdDto;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface MemberView extends UserIdDto {
  String getFirstName();

  String getLastName();
}

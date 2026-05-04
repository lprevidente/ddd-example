package com.lprevidente.orgcraft.team.application.projection;

import com.lprevidente.orgcraft.user.api.UserIdDto;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface MemberView extends UserIdDto {
  String getFirstName();

  String getLastName();
}

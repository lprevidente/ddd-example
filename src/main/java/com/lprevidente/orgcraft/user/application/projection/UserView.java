package com.lprevidente.orgcraft.user.application.projection;

import com.lprevidente.orgcraft.user.api.UserId;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface UserView {
  UserId getId();

  String getFirstName();

  String getLastName();
}

package com.lprevidente.ddd_example.user.application.projection;

import com.lprevidente.ddd_example.user.domain.UserId;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface UserView {
  UserId getId();

  String getFirstName();

  String getLastName();
}

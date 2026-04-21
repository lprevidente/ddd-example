package com.lprevidente.ddd_example.user.application.dto;

import com.lprevidente.ddd_example.user.domain.UserId;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface UserInfoDto {
  UserId getId();

  String getFirstName();

  String getLastName();
}

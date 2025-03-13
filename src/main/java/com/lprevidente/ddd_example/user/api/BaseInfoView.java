package com.lprevidente.ddd_example.user.api;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.lprevidente.ddd_example.user.domain.User;

@EntityView(User.class)
public interface BaseInfoView {

  @IdMapping
  @JsonUnwrapped
  UserIdView getId();
}

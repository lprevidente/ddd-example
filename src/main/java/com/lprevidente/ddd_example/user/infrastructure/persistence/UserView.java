package com.lprevidente.ddd_example.user.infrastructure.persistence;

import com.blazebit.persistence.view.EntityView;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.lprevidente.ddd_example.user.api.BaseInfoView;
import com.lprevidente.ddd_example.user.domain.User;

@EntityView(User.class)
public interface UserView extends BaseInfoView {

  String getFirstName();

  String getLastName();

  @JsonUnwrapped
  EmailView getEmail();
}

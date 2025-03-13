package com.lprevidente.ddd_example.user.infrastructure.persistence;

import com.blazebit.persistence.view.EntityView;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lprevidente.ddd_example.user.domain.Email;

@EntityView(Email.class)
public interface EmailView {

  @JsonProperty("value")
  String getValue();
}

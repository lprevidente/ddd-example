package com.lprevidente.ddd_example.user.api;

import com.blazebit.persistence.view.EntityView;
import com.lprevidente.ddd_example.user.domain.UserId;
import java.util.UUID;

@EntityView(UserId.class)
public interface UserIdView {

  UUID getId();
}

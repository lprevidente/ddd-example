package com.lprevidente.ddd_example.team.infrastructure.persistence;

import com.blazebit.persistence.view.EntityView;
import com.lprevidente.ddd_example.user.api.BaseInfoView;
import com.lprevidente.ddd_example.user.domain.User;

@EntityView(User.class)
public interface MemberInfoView extends BaseInfoView {

  String getFirstName();

  String getLastName();
}

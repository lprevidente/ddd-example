package com.lprevidente.ddd_example.team.infrastructure.persistence;

import com.blazebit.persistence.view.EntityView;
import com.lprevidente.ddd_example.team.domain.TeamId;
import java.util.UUID;

@EntityView(TeamId.class)
public interface TeamViewId {
  UUID getId();
}

package com.lprevidente.ddd_example.office.application.projection;

import com.lprevidente.ddd_example.user.api.UserId;
import java.time.Instant;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface OfficeMemberView {
  UserId getUserId();

  Instant getAssignedAt();
}

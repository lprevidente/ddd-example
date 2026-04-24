package com.lprevidente.ddd_example.office.application.projection;

import com.lprevidente.ddd_example.office.domain.OfficeAssignmentId;
import com.lprevidente.ddd_example.office.domain.OfficeId;
import com.lprevidente.ddd_example.user.api.UserId;
import java.time.Instant;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface OfficeAssignmentView {
  OfficeAssignmentId getId();

  OfficeId getOfficeId();

  UserId getUserId();

  Instant getAssignedAt();

  Instant getUnassignedAt();
}

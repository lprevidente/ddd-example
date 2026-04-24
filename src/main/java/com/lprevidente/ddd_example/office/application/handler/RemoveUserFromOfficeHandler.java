package com.lprevidente.ddd_example.office.application.handler;

import com.lprevidente.ddd_example.office.application.command.RemoveUserFromOffice;
import com.lprevidente.ddd_example.office.domain.OfficeAssignments;
import com.lprevidente.ddd_example.office.domain.OfficeId;
import com.lprevidente.ddd_example.user.api.UserId;
import com.lprevidente.ddd_example.office.domain.exception.OfficeAssignmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class RemoveUserFromOfficeHandler {

  private final OfficeAssignments officeAssignments;

  @CommandHandler
  public void handle(RemoveUserFromOffice command) {
    final var officeId = new OfficeId(command.officeId());
    final var userId = new UserId(command.userId());
    final var assignment =
        officeAssignments
            .findByOfficeIdAndUserIdAndUnassignedAtIsNull(officeId, userId)
            .orElseThrow(() -> new OfficeAssignmentNotFoundException(officeId, userId));
    assignment.unassign();
    officeAssignments.save(assignment);
  }
}

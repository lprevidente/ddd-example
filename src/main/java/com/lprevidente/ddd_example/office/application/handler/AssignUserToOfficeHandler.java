package com.lprevidente.ddd_example.office.application.handler;

import com.lprevidente.ddd_example.office.application.command.AssignUserToOffice;
import com.lprevidente.ddd_example.office.domain.OfficeAssignment;
import com.lprevidente.ddd_example.office.domain.OfficeAssignmentId;
import com.lprevidente.ddd_example.office.domain.OfficeAssignments;
import com.lprevidente.ddd_example.office.domain.OfficeId;
import com.lprevidente.ddd_example.office.domain.Offices;
import com.lprevidente.ddd_example.user.api.UserId;
import com.lprevidente.ddd_example.office.domain.exception.OfficeNotFoundException;
import com.lprevidente.ddd_example.user.api.UserApi;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.jmolecules.ddd.annotation.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class AssignUserToOfficeHandler {

  private final Offices offices;
  private final OfficeAssignments officeAssignments;
  private final UserApi userApi;

  @CommandHandler
  public OfficeAssignmentId handle(AssignUserToOffice command) {
    final var officeId = new OfficeId(command.officeId());
    final var userId = new UserId(command.userId());

    if (!offices.existsById(officeId)) throw new OfficeNotFoundException(officeId);
    Assert.isTrue(
        userApi.existsById(command.userId()),
        "User with ID %s does not exist".formatted(command.userId()));

    officeAssignments
        .findByUserIdAndUnassignedAtIsNull(userId)
        .ifPresent(
            prev -> {
              prev.unassign();
              officeAssignments.save(prev);
            });

    final var assignment = new OfficeAssignment(officeId, userId);
    officeAssignments.save(assignment);
    return assignment.getId();
  }
}

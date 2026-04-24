package com.lprevidente.ddd_example.office.infrastructure.rest;

import com.lprevidente.ddd_example.office.application.command.AssignUserToOffice;
import com.lprevidente.ddd_example.office.application.command.RemoveUserFromOffice;
import com.lprevidente.ddd_example.office.application.handler.AssignUserToOfficeHandler;
import com.lprevidente.ddd_example.office.application.handler.RemoveUserFromOfficeHandler;
import com.lprevidente.ddd_example.office.application.projection.OfficeAssignmentView;
import com.lprevidente.ddd_example.office.application.projection.OfficeMemberView;
import com.lprevidente.ddd_example.office.application.query.OfficeAssignmentQueryService;
import com.lprevidente.ddd_example.office.domain.OfficeAssignmentId;
import com.lprevidente.ddd_example.office.domain.OfficeId;
import com.lprevidente.ddd_example.user.api.UserId;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class OfficeAssignmentController {

  private final OfficeAssignmentQueryService queryService;
  private final AssignUserToOfficeHandler assignHandler;
  private final RemoveUserFromOfficeHandler removeHandler;

  @GetMapping("/api/v1/offices/{officeId}/members")
  Collection<OfficeMemberView> getCurrentMembers(@PathVariable OfficeId officeId) {
    return queryService.getCurrentMembers(officeId);
  }

  @PostMapping("/api/v1/offices/{officeId}/members")
  @ResponseStatus(HttpStatus.CREATED)
  OfficeAssignmentId assignUser(
      @PathVariable UUID officeId, @RequestBody @Valid AssignUserToOffice command) {
    return assignHandler.handle(command);
  }

  @DeleteMapping("/api/v1/offices/{officeId}/members/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void removeUser(@PathVariable UUID officeId, @PathVariable UserId userId) {
    removeHandler.handle(new RemoveUserFromOffice(officeId, userId.id()));
  }

  @GetMapping("/api/v1/users/{userId}/office-history")
  Collection<OfficeAssignmentView> getAssignmentHistory(@PathVariable UserId userId) {
    return queryService.getAssignmentHistory(userId);
  }
}

package com.lprevidente.ddd_example.office.domain.exception;

import com.lprevidente.ddd_example.common.exception.DomainException;
import com.lprevidente.ddd_example.office.domain.OfficeId;
import com.lprevidente.ddd_example.user.api.UserId;
import org.springframework.http.HttpStatus;

public class OfficeAssignmentNotFoundException extends DomainException {

  public OfficeAssignmentNotFoundException(OfficeId officeId, UserId userId) {
    super(
        "No active assignment found for user %s in office %s".formatted(userId.id(), officeId.id()),
        "OFFICE_ASSIGNMENT_NOT_FOUND",
        HttpStatus.NOT_FOUND,
        "Office Assignment Not Found");
  }
}

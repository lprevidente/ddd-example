package com.lprevidente.ddd_example.office.domain.exception;

import com.lprevidente.ddd_example.common.exception.DomainException;
import com.lprevidente.ddd_example.office.domain.OfficeId;
import org.springframework.http.HttpStatus;

public class OfficeNotFoundException extends DomainException {

  public OfficeNotFoundException(OfficeId id) {
    super(
        "Office with ID %s not found".formatted(id.id()),
        "OFFICE_NOT_FOUND",
        HttpStatus.NOT_FOUND,
        "Office Not Found");
  }
}

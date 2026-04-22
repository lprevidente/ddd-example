package com.lprevidente.ddd_example.office.application.handler;

import com.lprevidente.ddd_example.office.application.command.CreateOffice;
import com.lprevidente.ddd_example.office.domain.Office;
import com.lprevidente.ddd_example.office.domain.OfficeId;
import com.lprevidente.ddd_example.office.domain.Offices;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class CreateOfficeHandler {
  private final Offices offices;

  @CommandHandler
  public OfficeId handle(CreateOffice command) {
    final var office = new Office(command.name(), command.address());
    offices.save(office);
    return office.getId();
  }
}

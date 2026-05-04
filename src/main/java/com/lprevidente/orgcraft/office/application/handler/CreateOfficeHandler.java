package com.lprevidente.orgcraft.office.application.handler;

import com.lprevidente.orgcraft.office.application.command.CreateOffice;
import com.lprevidente.orgcraft.office.domain.Office;
import com.lprevidente.orgcraft.office.domain.OfficeId;
import com.lprevidente.orgcraft.office.domain.Offices;
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

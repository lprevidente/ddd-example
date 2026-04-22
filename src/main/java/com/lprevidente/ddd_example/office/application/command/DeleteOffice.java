package com.lprevidente.ddd_example.office.application.command;

import com.lprevidente.ddd_example.office.domain.OfficeId;
import jakarta.validation.constraints.NotNull;
import org.jmolecules.architecture.cqrs.Command;

@Command
public record DeleteOffice(@NotNull OfficeId id) {}

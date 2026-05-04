package com.lprevidente.orgcraft.office.application.command;

import com.lprevidente.orgcraft.office.domain.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jmolecules.architecture.cqrs.Command;

@Command
public record CreateOffice(@NotBlank String name, @Valid @NotNull Address address) {}

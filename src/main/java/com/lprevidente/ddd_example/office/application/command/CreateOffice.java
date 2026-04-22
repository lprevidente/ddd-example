package com.lprevidente.ddd_example.office.application.command;

import com.lprevidente.ddd_example.office.domain.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jmolecules.architecture.cqrs.Command;

@Command
public record CreateOffice(@NotBlank String name, @Valid @NotNull Address address) {}

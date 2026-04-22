package com.lprevidente.ddd_example.office.application.projection;

import com.lprevidente.ddd_example.office.domain.Address;
import com.lprevidente.ddd_example.office.domain.OfficeId;
import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public interface OfficeView {
  OfficeId getId();

  String getName();

  Address getAddress();
}

package com.lprevidente.ddd_example.office.application.query;

import com.lprevidente.ddd_example.office.application.projection.OfficeView;
import com.lprevidente.ddd_example.office.domain.OfficeId;
import com.lprevidente.ddd_example.office.domain.exception.OfficeNotFoundException;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class OfficeQueryService {
  private final OfficeReadRepository offices;

  public Collection<OfficeView> findAll() {
    return offices.findAllBy(OfficeView.class);
  }

  public OfficeView getById(OfficeId id) {
    return offices
        .findById(id, OfficeView.class)
        .orElseThrow(() -> new OfficeNotFoundException(id));
  }
}

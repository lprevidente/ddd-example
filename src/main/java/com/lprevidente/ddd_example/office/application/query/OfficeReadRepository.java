package com.lprevidente.ddd_example.office.application.query;

import com.lprevidente.ddd_example.office.domain.Office;
import com.lprevidente.ddd_example.office.domain.OfficeId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface OfficeReadRepository extends Repository<Office, OfficeId> {

  <T> List<T> findAllBy(Class<T> projection);

  <T> Optional<T> findById(OfficeId id, Class<T> projection);
}

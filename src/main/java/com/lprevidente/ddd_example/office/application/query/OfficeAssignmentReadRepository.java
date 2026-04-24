package com.lprevidente.ddd_example.office.application.query;

import com.lprevidente.ddd_example.office.domain.OfficeAssignment;
import com.lprevidente.ddd_example.office.domain.OfficeAssignmentId;
import com.lprevidente.ddd_example.office.domain.OfficeId;
import com.lprevidente.ddd_example.user.api.UserId;
import java.util.List;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface OfficeAssignmentReadRepository
    extends Repository<OfficeAssignment, OfficeAssignmentId> {

  <T> List<T> findAllByOfficeIdAndUnassignedAtIsNull(OfficeId officeId, Class<T> projection);

  <T> List<T> findAllByUserId(UserId userId, Class<T> projection);
}

package com.lprevidente.ddd_example.office.domain;

import java.util.Optional;
import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lprevidente.ddd_example.user.api.UserId;

@Repository
public interface OfficeAssignments extends JpaRepository<OfficeAssignment, OfficeAssignmentId> {

  Optional<OfficeAssignment> findByUserIdAndUnassignedAtIsNull(UserId userId);

  Optional<OfficeAssignment> findByOfficeIdAndUserIdAndUnassignedAtIsNull(
      OfficeId officeId, UserId userId);
}

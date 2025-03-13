package com.lprevidente.ddd_example.team.infrastructure.persistence;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import com.lprevidente.ddd_example.team.domain.TeamId;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface TeamViewRepository extends EntityViewRepository<TeamView, TeamId> {

  List<TeamView> findAll();
}

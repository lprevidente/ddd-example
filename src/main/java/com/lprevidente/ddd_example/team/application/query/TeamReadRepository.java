package com.lprevidente.ddd_example.team.application.query;

import com.lprevidente.ddd_example.team.domain.Team;
import com.lprevidente.ddd_example.team.domain.TeamId;
import java.util.List;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface TeamReadRepository extends Repository<Team, TeamId> {

  <T> List<T> findAllBy(Class<T> projection);
}

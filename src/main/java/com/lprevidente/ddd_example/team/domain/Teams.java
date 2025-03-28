package com.lprevidente.ddd_example.team.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface Teams extends JpaRepository<Team, TeamId> {
  <T> List<T> findAllBy(Class<T> clazz);
}

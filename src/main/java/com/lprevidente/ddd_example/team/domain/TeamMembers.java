package com.lprevidente.ddd_example.team.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TeamMembers extends JpaRepository<TeamMember, TeamMemberId> {

  <T> List<T> findAllById_TeamId(TeamId id, Class<T> clazz);
}

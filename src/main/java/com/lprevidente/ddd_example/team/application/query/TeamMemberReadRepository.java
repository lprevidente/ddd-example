package com.lprevidente.ddd_example.team.application.query;

import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.team.domain.TeamMember;
import com.lprevidente.ddd_example.team.domain.TeamMemberId;
import java.util.List;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface TeamMemberReadRepository extends Repository<TeamMember, TeamMemberId> {

  <T> List<T> findAllById_TeamId(TeamId id, Class<T> projection);
}

package com.lprevidente.ddd_example.team.infrastructure.persistence;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.team.domain.TeamMemberId;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface TeamMemberViewRepository
    extends EntityViewRepository<TeamMemberView, TeamMemberId> {

  List<TeamMemberView> findById_TeamId(TeamId teamId);
}

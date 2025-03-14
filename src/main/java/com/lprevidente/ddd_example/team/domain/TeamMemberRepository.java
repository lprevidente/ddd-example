package com.lprevidente.ddd_example.team.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberId> {}

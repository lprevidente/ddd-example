package com.lprevidente.ddd_example.team.domain;

import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TeamMembers extends JpaRepository<TeamMember, TeamMemberId> {}

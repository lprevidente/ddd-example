package com.lprevidente.ddd_example.team.domain;

import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface Teams extends JpaRepository<Team, TeamId> {}

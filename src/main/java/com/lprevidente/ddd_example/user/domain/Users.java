package com.lprevidente.ddd_example.user.domain;

import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface Users extends JpaRepository<User, UserId> {

  boolean existsByEmail(Email email);
}

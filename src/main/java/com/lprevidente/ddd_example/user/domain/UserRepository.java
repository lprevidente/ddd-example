package com.lprevidente.ddd_example.user.domain;

import com.lprevidente.ddd_example.user.infrastructure.persistence.CustomUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, UserId>, CustomUserRepository {

  @Query("select exists (select 1 from User where email = :email)")
  boolean existsByEmail(String email);
}

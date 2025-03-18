package com.lprevidente.ddd_example.user.domain;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface Users extends JpaRepository<User, UserId> {

  <T> List<T> findAllBy(Class<T> clazz);

  <T> List<T> findAllByIdIn(Collection<UserId> ids, Class<T> clazz);

  <T> Optional<T> findById(UserId id, Class<T> clazz);

  boolean existsByEmail(Email email);
}

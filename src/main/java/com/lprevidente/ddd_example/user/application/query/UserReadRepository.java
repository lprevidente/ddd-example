package com.lprevidente.ddd_example.user.application.query;

import com.lprevidente.ddd_example.user.domain.User;
import com.lprevidente.ddd_example.user.api.UserId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserReadRepository extends Repository<User, UserId> {

  <T> List<T> findAllBy(Class<T> projection);

  <T> List<T> findAllByIdIn(Collection<UserId> ids, Class<T> projection);

  <T> Optional<T> findById(UserId id, Class<T> projection);
}

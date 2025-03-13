package com.lprevidente.ddd_example.user.infrastructure.persistence;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.lprevidente.ddd_example.user.api.BaseInfoView;
import com.lprevidente.ddd_example.user.domain.User;
import jakarta.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserRepositoryImpl implements CustomUserRepository {

  private final CriteriaBuilderFactory cbf;
  private final EntityViewManager evm;
  private final EntityManager em;

  @Override
  public <T extends BaseInfoView> List<T> fetchAll(Class<T> clazz) {
    final var cb = cbf.create(em, User.class);
    final var builder = evm.applySetting(EntityViewSetting.create(clazz), cb);
    return builder.getResultList();
  }

  @Override
  public <T extends BaseInfoView> List<T> fetchAllById(Collection<UUID> ids, Class<T> clazz) {
    final var cb = cbf.create(em, User.class);
    // TODO: add where id in (?)
    final var builder = evm.applySetting(EntityViewSetting.create(clazz), cb);
    return builder.getResultList();
  }

  @Override
  public <T extends BaseInfoView> Optional<T> fetchById(UUID id, Class<T> clazz) {
    final var cb = cbf.create(em, User.class);
    final var builder = evm.applySetting(EntityViewSetting.create(clazz), cb);
    return builder.getResultStream().findFirst();
  }
}

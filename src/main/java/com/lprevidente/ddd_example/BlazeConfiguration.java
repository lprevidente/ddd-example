package com.lprevidente.ddd_example;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.integration.view.spring.EnableEntityViews;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.blazebit.persistence.spring.data.repository.config.EnableBlazeRepositories;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.spi.EntityViewConfiguration;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.query.QueryLookupStrategy;

/** Configuration for Blaze-Persistence Registers entity views and sets up required beans */
@Configuration
@EnableEntityViews("com.lprevidente")
@EnableBlazeRepositories(
    value = "com.lprevidente",
    queryLookupStrategy = QueryLookupStrategy.Key.CREATE)
public class BlazeConfiguration {

  @Bean
  @Lazy(false)
  public CriteriaBuilderFactory createCriteriaBuilderFactory(
      EntityManagerFactory entityManagerFactory) {
    CriteriaBuilderConfiguration config = Criteria.getDefault();
    return config.createCriteriaBuilderFactory(entityManagerFactory);
  }

  @Bean
  public EntityViewManager createEntityViewManager(
      CriteriaBuilderFactory cbf, //
      EntityViewConfiguration entityViewConfiguration) {
    return entityViewConfiguration.createEntityViewManager(cbf);
  }
}

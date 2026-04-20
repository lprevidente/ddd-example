package com.lprevidente.ddd_example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

class VeryModulithTest extends BaseIntegrationTest {

  @Test
  void createApplicationModuleModel() {
    ApplicationModules modules = ApplicationModules.of(Application.class);
    modules.forEach(System.out::println);
    modules.verify();
  }
}

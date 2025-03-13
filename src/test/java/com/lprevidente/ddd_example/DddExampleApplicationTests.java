package com.lprevidente.ddd_example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class DddExampleApplicationTests {

  @Test
  void verifyModules() {
    ApplicationModules.of(DddExampleApplication.class).verify();
  }
}

package com.lprevidente.ddd_example;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.jmolecules.archunit.JMoleculesDddRules;
import org.junit.jupiter.api.Test;

class DddRulesTest {

  private static final String BASE_PACKAGE = "com.lprevidente.ddd_example";

  @Test
  void dddRulesAreSatisfied() {
    var classes =
        new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(BASE_PACKAGE);

    JMoleculesDddRules.all().check(classes);
  }
}

package com.lprevidente.ddd_example.office.domain;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record Address(String address, String city, String country) {}

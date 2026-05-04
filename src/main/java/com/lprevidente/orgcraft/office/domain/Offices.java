package com.lprevidente.orgcraft.office.domain;

import org.jmolecules.ddd.annotation.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface Offices extends JpaRepository<Office, OfficeId> {}

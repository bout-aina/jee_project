package com.example.patientmvc.sec.repositories;

import com.example.patientmvc.sec.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {

AppRole findByRoleName(String roleName);
}

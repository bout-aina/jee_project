package com.example.patientmvc.sec.repositories;

import com.example.patientmvc.sec.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {

AppUser findByUsername(String username);
}

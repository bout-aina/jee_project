package com.example.patientmvc.repositories;


import com.example.patientmvc.entities.Medecin;
import com.example.patientmvc.entities.RendezVous;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long>
{
    Page<RendezVous> findByNumRdvContains( String key,Pageable pageable);


}

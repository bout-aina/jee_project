package com.example.patientmvc.repositories;


import com.example.patientmvc.entities.Consultation;
import com.example.patientmvc.entities.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long>
{
    Page<Consultation> findByRapportContains(String kw, Pageable pageable);
}

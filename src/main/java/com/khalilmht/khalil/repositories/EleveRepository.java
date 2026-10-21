package com.khalilmht.khalil.repositories;

import com.khalilmht.khalil.entities.Eleve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EleveRepository extends JpaRepository<Eleve, Long>, JpaSpecificationExecutor<Eleve> {

    boolean existsByMatricule(String matricule);

    boolean existsByEmail(String email);

    Optional<Eleve> findByMatricule(String matricule);

    // Recherche par nom ou prénom avec pagination
    Page<Eleve> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom, Pageable pageable);
}
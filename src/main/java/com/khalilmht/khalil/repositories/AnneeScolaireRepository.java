package com.khalilmht.khalil.repositories;


import com.khalilmht.khalil.entities.AnneeScolaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnneeScolaireRepository extends JpaRepository<AnneeScolaire, Long> {

    boolean existsByLibelle(String libelle);

    Optional<AnneeScolaire> findByLibelle(String libelle);

    // Permet de récupérer rapidement l'année scolaire active en cours
    Optional<AnneeScolaire> findByEstActiveTrue();
}
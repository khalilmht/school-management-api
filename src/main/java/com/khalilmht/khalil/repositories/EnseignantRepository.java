package com.khalilmht.khalil.repositories;

import com.khalilmht.khalil.entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    boolean existsByEmail(String email);
    Optional<Enseignant> findByMatriculeEmploye(String matriculeEmploye);
}
package com.khalilmht.khalil.repositories;

import com.khalilmht.khalil.entities.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    Optional<Absence> findByEleveIdAndAnneeScolaireIdAndPeriode(Long eleveId, Long anneeScolaireId, String periode);
}
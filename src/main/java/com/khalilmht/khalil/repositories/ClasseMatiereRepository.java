package com.khalilmht.khalil.repositories;

import com.khalilmht.khalil.entities.ClasseMatiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClasseMatiereRepository extends JpaRepository<ClasseMatiere, Long> {
    List<ClasseMatiere> findByClasseIdAndAnneeScolaireId(Long classeId, Long anneeScolaireId);
    List<ClasseMatiere> findByEnseignantId(Long enseignantId);
}
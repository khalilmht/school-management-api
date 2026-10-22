package com.khalilmht.khalil.repositories;


import com.khalilmht.khalil.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByEleveIdAndEvaluationClasseMatiereIdAndEvaluationPeriode(Long eleveId, Long classeMatiereId, String periode);
    Optional<Note> findByEvaluationIdAndEleveId(Long evaluationId, Long eleveId);
    List<Note> findByEvaluationId(Long evaluationId);
    List<Note> findByEleveId(Long eleveId);
}
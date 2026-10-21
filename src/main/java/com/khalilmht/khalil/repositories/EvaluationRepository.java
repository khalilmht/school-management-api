package com.khalilmht.khalil.repositories;

import com.khalilmht.khalil.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByClasseMatiereIdAndPeriode(Long classeMatiereId, String periode);
}
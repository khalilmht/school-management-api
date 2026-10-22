package com.khalilmht.khalil.controller;

import com.khalilmht.khalil.dto.EvaluationRequestDto;
import com.khalilmht.khalil.entities.ClasseMatiere;
import com.khalilmht.khalil.entities.Evaluation;
import com.khalilmht.khalil.repositories.ClasseMatiereRepository;
import com.khalilmht.khalil.repositories.EvaluationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluations")
@CrossOrigin(origins = "*")
public class EvaluationController {

    private final EvaluationRepository evaluationRepository;
    private final ClasseMatiereRepository classeMatiereRepository;

    public EvaluationController(EvaluationRepository evaluationRepository, ClasseMatiereRepository classeMatiereRepository) {
        this.evaluationRepository = evaluationRepository;
        this.classeMatiereRepository = classeMatiereRepository;
    }

    @GetMapping
    public ResponseEntity<List<Evaluation>> listerEvaluations() {
        return ResponseEntity.ok(evaluationRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> creerEvaluation(@RequestBody EvaluationRequestDto dto) {
        // Vérification de l'existence de la classe-matière pour éviter toute erreur 500
        ClasseMatiere classeMatiere = classeMatiereRepository.findById(dto.getClasseMatiereId())
                .orElseThrow(() -> new RuntimeException("Classe-Matière introuvable avec l'ID : " + dto.getClasseMatiereId()));

        Evaluation evaluation = Evaluation.builder()
                .intitule(dto.getIntitule())
                .typeEvaluation(dto.getTypeEvaluation())
                .dateEvaluation(dto.getDateEvaluation())
                .noteMaximale(dto.getNoteMaximale() != null ? dto.getNoteMaximale() : BigDecimal.valueOf(20.00))
                .coefficient(dto.getCoefficient() != null ? dto.getCoefficient() : BigDecimal.valueOf(1.0))
                .periode(dto.getPeriode())
                .classeMatiere(classeMatiere)
                .estValidee(dto.getEstValidee() != null ? dto.getEstValidee() : false)
                .build();

        Evaluation savedEvaluation = evaluationRepository.save(evaluation);
        return ResponseEntity.ok(savedEvaluation);
    }
}
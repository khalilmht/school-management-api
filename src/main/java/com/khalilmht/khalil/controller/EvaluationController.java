package com.khalilmht.khalil.controller;

import com.khalilmht.khalil.entities.Evaluation;
import com.khalilmht.khalil.repositories.EvaluationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluations")
@CrossOrigin(origins = "*")
public class EvaluationController {

    private final EvaluationRepository evaluationRepository;

    public EvaluationController(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @GetMapping
    public ResponseEntity<List<Evaluation>> listerEvaluations() {
        return ResponseEntity.ok(evaluationRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Evaluation> creerEvaluation(@RequestBody Evaluation evaluation) {
        return ResponseEntity.ok(evaluationRepository.save(evaluation));
    }
}
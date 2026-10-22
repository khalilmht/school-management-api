package com.khalilmht.khalil.controller;

import com.khalilmht.khalil.entities.Enseignant;
import com.khalilmht.khalil.repositories.EnseignantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enseignants")
@CrossOrigin(origins = "*")
public class EnseignantController {

    private final EnseignantRepository enseignantRepository;

    public EnseignantController(EnseignantRepository enseignantRepository) {
        this.enseignantRepository = enseignantRepository;
    }

    @GetMapping
    public ResponseEntity<List<Enseignant>> listerTous() {
        return ResponseEntity.ok(enseignantRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Enseignant> creerEnseignant(@RequestBody Enseignant enseignant) {
        return ResponseEntity.ok(enseignantRepository.save(enseignant));
    }
}
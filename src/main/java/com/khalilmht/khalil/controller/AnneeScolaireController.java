package com.khalilmht.khalil.controller;

import com.khalilmht.khalil.entities.AnneeScolaire;
import com.khalilmht.khalil.repositories.AnneeScolaireRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/annees-scolaires")
@CrossOrigin(origins = "*")
public class AnneeScolaireController {

    private final AnneeScolaireRepository anneeScolaireRepository;

    public AnneeScolaireController(AnneeScolaireRepository anneeScolaireRepository) {
        this.anneeScolaireRepository = anneeScolaireRepository;
    }

    @GetMapping
    public ResponseEntity<List<AnneeScolaire>> listerAnnees() {
        return ResponseEntity.ok(anneeScolaireRepository.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<AnneeScolaire> obtenirAnneeActive() {
        return anneeScolaireRepository.findByEstActiveTrue()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
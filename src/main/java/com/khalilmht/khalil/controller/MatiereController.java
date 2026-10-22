package com.khalilmht.khalil.controller;


import com.khalilmht.khalil.entities.Matiere;
import com.khalilmht.khalil.repositories.MatiereRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matieres")
@CrossOrigin(origins = "*")
public class MatiereController {

    private final MatiereRepository matiereRepository;

    public MatiereController(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    @GetMapping
    public ResponseEntity<List<Matiere>> listerToutes() {
        return ResponseEntity.ok(matiereRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Matiere> creerMatiere(@RequestBody Matiere matiere) {
        return ResponseEntity.ok(matiereRepository.save(matiere));
    }
}
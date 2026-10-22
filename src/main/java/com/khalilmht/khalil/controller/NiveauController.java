package com.khalilmht.khalil.controller;


import com.khalilmht.khalil.controller.dto.NiveauRequestDto;
import com.khalilmht.khalil.entities.Niveau;
import com.khalilmht.khalil.repositories.NiveauRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/niveaux")
@CrossOrigin(origins = "*")
public class NiveauController {

    private final NiveauRepository niveauRepository;

    public NiveauController(NiveauRepository niveauRepository) {
        this.niveauRepository = niveauRepository;
    }

    @GetMapping
    public ResponseEntity<List<Niveau>> listerTousLesNiveaux() {
        return ResponseEntity.ok(niveauRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> creerNiveau(@RequestBody NiveauRequestDto dto) {
        if (niveauRepository.existsByCode(dto.getCode())) {
            return ResponseEntity.badRequest().body("Erreur : Un niveau avec le code '" + dto.getCode() + "' existe déjà.");
        }

        Niveau niveau = Niveau.builder()
                .code(dto.getCode())
                .nom(dto.getNom())
                .build();

        Niveau nouveauNiveau = niveauRepository.save(niveau);
        return ResponseEntity.ok(nouveauNiveau);
    }
}
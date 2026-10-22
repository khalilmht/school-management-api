package com.khalilmht.khalil.controller;

import com.khalilmht.khalil.controller.dto.ClasseRequestDto;
import com.khalilmht.khalil.entities.Classe;
import com.khalilmht.khalil.entities.Niveau;
import com.khalilmht.khalil.repositories.ClasseRepository;
import com.khalilmht.khalil.repositories.NiveauRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classes")
@CrossOrigin(origins = "*")
public class ClasseController {

    private final ClasseRepository classeRepository;
    private final NiveauRepository niveauRepository;

    public ClasseController(ClasseRepository classeRepository, NiveauRepository niveauRepository) {
        this.classeRepository = classeRepository;
        this.niveauRepository = niveauRepository;
    }

    @GetMapping
    public ResponseEntity<List<Classe>> listerToutesLesClasses() {
        return ResponseEntity.ok(classeRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> creerClasse(@RequestBody ClasseRequestDto dto) {
        if (classeRepository.existsByCode(dto.getCode())) {
            return ResponseEntity.badRequest().body("Erreur : Une classe avec le code '" + dto.getCode() + "' existe déjà.");
        }

        Niveau niveau = niveauRepository.findById(dto.getNiveauId())
                .orElseThrow(() -> new RuntimeException("Niveau introuvable avec l'ID : " + dto.getNiveauId()));

        Classe classe = Classe.builder()
                .code(dto.getCode())
                .nom(dto.getNom())
                .niveau(niveau)
                .capaciteMaximale(dto.getCapaciteMaximale() != null ? dto.getCapaciteMaximale() : 40)
                .build();

        Classe nouvelleClasse = classeRepository.save(classe);
        return ResponseEntity.ok(nouvelleClasse);
    }
}
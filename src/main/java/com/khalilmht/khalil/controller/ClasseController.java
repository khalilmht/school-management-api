package com.khalilmht.khalil.controller;

import com.khalilmht.khalil.entities.Classe;
import com.khalilmht.khalil.repositories.ClasseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classes")
@CrossOrigin(origins = "*")
public class ClasseController {

    private final ClasseRepository classeRepository;

    public ClasseController(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Classe>> listerToutesLesClasses() {
        return ResponseEntity.ok(classeRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Classe> creerClasse(@RequestBody Classe classe) {
        Classe nouvelleClasse = classeRepository.save(classe);
        return ResponseEntity.ok(nouvelleClasse);
    }
}
package com.khalilmht.khalil.controller;

import com.khalilmht.khalil.entities.Eleve;
import com.khalilmht.khalil.repositories.EleveRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eleves")
@CrossOrigin(origins = "*")
public class EleveController {

    private final EleveRepository eleveRepository;

    public EleveController(EleveRepository eleveRepository) {
        this.eleveRepository = eleveRepository;
    }

    @GetMapping
    public ResponseEntity<List<Eleve>> listerTousLesEleves() {
        return ResponseEntity.ok(eleveRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> creerEleve(@RequestBody Eleve eleve) {
        // Vérification de l'unicité du matricule avant l'enregistrement
        if (eleve.getMatricule() != null && eleveRepository.existsByMatricule(eleve.getMatricule())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erreur : Un élève avec le matricule '" + eleve.getMatricule() + "' existe déjà.");
        }

        // Sécurité pour éviter le null sur le statut
        if (eleve.getStatut() == null) {
            eleve.setStatut(Eleve.StatutEleve.ACTIF);
        }

        Eleve nouveauEleve = eleveRepository.save(eleve);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauEleve);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifierEleve(@PathVariable Long id, @RequestBody Eleve eleveDetails) {
        return eleveRepository.findById(id)
                .map(eleve -> {
                    // Vérifier si le nouveau matricule est déjà pris par un autre élève
                    if (eleveDetails.getMatricule() != null &&
                            !eleve.getMatricule().equals(eleveDetails.getMatricule()) &&
                            eleveRepository.existsByMatricule(eleveDetails.getMatricule())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("Erreur : Ce matricule est déjà utilisé par un autre élève.");
                    }

                    eleve.setMatricule(eleveDetails.getMatricule());
                    eleve.setNom(eleveDetails.getNom());
                    eleve.setPrenom(eleveDetails.getPrenom());
                    eleve.setSexe(eleveDetails.getSexe());
                    eleve.setLieuNaissance(eleveDetails.getLieuNaissance());
                    eleve.setEmail(eleveDetails.getEmail());
                    eleve.setDateNaissance(eleveDetails.getDateNaissance());

                    if (eleveDetails.getStatut() != null) {
                        eleve.setStatut(eleveDetails.getStatut());
                    }

                    Eleve eleveMisAJour = eleveRepository.save(eleve);
                    return ResponseEntity.ok(eleveMisAJour);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerEleve(@PathVariable Long id) {
        if (!eleveRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        eleveRepository.deleteById(id);
        return ResponseEntity.ok().body("Élève supprimé avec succès.");
    }
}
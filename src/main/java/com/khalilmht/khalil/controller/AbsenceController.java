package com.khalilmht.khalil.controller;
import com.khalilmht.khalil.entities.Absence;
import com.khalilmht.khalil.repositories.AbsenceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/absences")
@CrossOrigin(origins = "*")
public class AbsenceController {

    private final AbsenceRepository absenceRepository;

    public AbsenceController(AbsenceRepository absenceRepository) {
        this.absenceRepository = absenceRepository;
    }

    @GetMapping
    public ResponseEntity<List<Absence>> listerAbsences() {
        return ResponseEntity.ok(absenceRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Absence> enregistrerAbsence(@RequestBody Absence absence) {
        return ResponseEntity.ok(absenceRepository.save(absence));
    }
}
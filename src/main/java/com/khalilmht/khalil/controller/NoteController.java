package com.khalilmht.khalil.controller;

import com.khalilmht.khalil.dto.NoteRequestDto;
import com.khalilmht.khalil.entities.Eleve;
import com.khalilmht.khalil.entities.Evaluation;
import com.khalilmht.khalil.entities.Note;
import com.khalilmht.khalil.repositories.EleveRepository;
import com.khalilmht.khalil.repositories.EvaluationRepository;
import com.khalilmht.khalil.repositories.NoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notes")
@CrossOrigin(origins = "*")
public class NoteController {

    private final NoteRepository noteRepository;
    private final EleveRepository eleveRepository;
    private final EvaluationRepository evaluationRepository;

    public NoteController(NoteRepository noteRepository, EleveRepository eleveRepository, EvaluationRepository evaluationRepository) {
        this.noteRepository = noteRepository;
        this.eleveRepository = eleveRepository;
        this.evaluationRepository = evaluationRepository;
    }

    @GetMapping
    public ResponseEntity<List<Note>> listerNotes() {
        return ResponseEntity.ok(noteRepository.findAll());
    }

    @GetMapping("/eleve/{eleveId}")
    public ResponseEntity<List<Note>> listerNotesParEleve(@PathVariable Long eleveId) {
        return ResponseEntity.ok(noteRepository.findByEleveId(eleveId));
    }

    @PostMapping
    public ResponseEntity<?> enregistrerNote(@RequestBody NoteRequestDto dto) {
        // Vérifier l'élève
        Eleve eleve = eleveRepository.findById(dto.getEleveId())
                .orElseThrow(() -> new RuntimeException("Élève introuvable avec l'ID : " + dto.getEleveId()));

        // Vérifier l'évaluation
        Evaluation evaluation = evaluationRepository.findById(dto.getEvaluationId())
                .orElseThrow(() -> new RuntimeException("Évaluation introuvable avec l'ID : " + dto.getEvaluationId()));

        // Gérer l'unicité (Mise à jour si la note existe déjà pour ce duo Élève/Évaluation, sinon création)
        Optional<Note> noteExistante = noteRepository.findByEvaluationIdAndEleveId(dto.getEvaluationId(), dto.getEleveId());

        Note note;
        if (noteExistante.isPresent()) {
            note = noteExistante.get();
            note.setValeur(dto.getValeur());
            note.setAppreciation(dto.getAppreciation());
        } else {
            note = Note.builder()
                    .eleve(eleve)
                    .evaluation(evaluation)
                    .valeur(dto.getValeur())
                    .appreciation(dto.getAppreciation())
                    .build();
        }

        Note savedNote = noteRepository.save(note);
        return ResponseEntity.ok(savedNote);
    }
}
package com.khalilmht.khalil.controller;


import com.khalilmht.khalil.entities.Note;
import com.khalilmht.khalil.repositories.NoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@CrossOrigin(origins = "*")
public class NoteController {

    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
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
    public ResponseEntity<Note> enregistrerNote(@RequestBody Note note) {
        return ResponseEntity.ok(noteRepository.save(note));
    }
}
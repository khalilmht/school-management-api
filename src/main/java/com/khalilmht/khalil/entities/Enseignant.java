package com.khalilmht.khalil.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enseignants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricule_employe", nullable = false, unique = true, length = 50)
    private String matriculeEmploye;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 30)
    private String telephone;

    @Column(length = 100)
    private String specialty; // Ou "specialite"
}
package com.khalilmht.khalil.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "eleves")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Eleve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String matricule;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "lieu_naissance", nullable = false, length = 150)
    private String lieuNaissance;

    @Column(nullable = false, length = 10)
    private String sexe; // Ex: "M", "F"

    @Column(length = 50)
    @Builder.Default
    private String nationalite = "Tchadienne";

    @Column(columnDefinition = "TEXT")
    private String adresse;

    @Column(length = 30)
    private String telephone;

    @Column(length = 150, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatutEleve statut = StatutEleve.ACTIF;

    @CreationTimestamp
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    // Relation Many-to-Many avec les parents ou tuteurs légaux
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "eleves_responsables",
            joinColumns = @JoinColumn(name = "eleve_id"),
            inverseJoinColumns = @JoinColumn(name = "responsable_id")
    )
    @Builder.Default
    private Set<ResponsableLegal> responsablesLegaux = new HashSet<>();

    public enum StatutEleve {
        ACTIF, ARCHIVE, SUSPENDU, DIPLOME, TRANSFERE
    }
}
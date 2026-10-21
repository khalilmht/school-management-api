package com.khalilmht.khalil.entities;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "annees_scolaires")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnneeScolaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle", nullable = false, unique = true, length = 20)
    private String libelle; // Ex: "2025-2026"

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(name = "est_active", nullable = false)
    @Builder.Default
    private Boolean estActive = false; // Indique l'année académique courante

    @Column(name = "est_cloturee", nullable = false)
    @Builder.Default
    private Boolean estCloturee = false; // Verrouille l'année une fois le processus de clôture effectué
}
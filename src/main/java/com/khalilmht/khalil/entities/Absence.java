package com.khalilmht.khalil.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "absences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "eleve_id", nullable = false)
    private Eleve eleve;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "annee_scolaire_id", nullable = false)
    private AnneeScolaire anneeScolaire;

    @Column(nullable = false, length = 20)
    private String periode; // Ex: "TRIMESTRE_1"

    @Column(name = "heures_absence", nullable = false)
    @Builder.Default
    private Integer heuresAbsence = 0;

    @Column(name = "heures_justifiees", nullable = false)
    @Builder.Default
    private Integer heuresJustifiees = 0;

    @Column(name = "nombre_retards", nullable = false)
    @Builder.Default
    private Integer nombreRetards = 0;
}
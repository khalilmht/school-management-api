package com.khalilmht.khalil.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String code; // Ex: "6EME-A", "TERM-S"

    @Column(nullable = false, length = 100)
    private String nom; // Ex: "Sixième A", "Terminale Scientifique"

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "niveau_id", nullable = false)
    private Niveau niveau; // Liaison vers le niveau scolaire (ex: Sixième, Terminale)

    @Column(name = "capacite_maximale", nullable = false)
    private Integer capaciteMaximale; // Nombre maximal d'élèves autorisé dans la classe
}
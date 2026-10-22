package com.khalilmht.khalil.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Niveau niveau; // Liaison vers le niveau scolaire (ex: Sixième, Terminale)

    @Column(name = "capacite_maximale", nullable = false)
    private Integer capaciteMaximale; // Nombre maximal d'élèves autorisé dans la classe
}
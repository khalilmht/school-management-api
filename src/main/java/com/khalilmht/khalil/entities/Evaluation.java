package com.khalilmht.khalil.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "evaluations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String intitule;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_evaluation", nullable = false, length = 30)
    private TypeEvaluation typeEvaluation;

    @Column(name = "date_evaluation", nullable = false)
    private LocalDate dateEvaluation;

    @Column(name = "note_maximale", nullable = false, precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal noteMaximale = BigDecimal.valueOf(20.00);

    @Column(nullable = false, precision = 3, scale = 1)
    @Builder.Default
    private BigDecimal coefficient = BigDecimal.valueOf(1.0);

    @Column(nullable = false, length = 20)
    private String periode; // Ex: "TRIMESTRE_1"

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "classe_matiere_id", nullable = false)
    private ClasseMatiere classeMatiere;

    @Column(name = "est_validee", nullable = false)
    @Builder.Default
    private Boolean estValidee = false;

    public enum TypeEvaluation {
        DEVOIR_SURVEILLE, INTERROGATION, COMPOSITION, EXAMEN, PRATIQUE, AUTRE
    }
}
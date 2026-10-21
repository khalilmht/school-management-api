package com.khalilmht.khalil.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"evaluation_id", "eleve_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "eleve_id", nullable = false)
    private Eleve eleve;

    @Column(precision = 5, scale = 2)
    private BigDecimal valeur;

    @Column(columnDefinition = "TEXT")
    private String appreciation;

    @UpdateTimestamp
    @Column(name = "date_saisie", nullable = false)
    private LocalDateTime dateSaisie;
}
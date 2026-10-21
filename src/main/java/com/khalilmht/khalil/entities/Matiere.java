package com.khalilmht.khalil.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "matieres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @Column(nullable = false, length = 100)
    private String intitule;

    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal coefficient;
}
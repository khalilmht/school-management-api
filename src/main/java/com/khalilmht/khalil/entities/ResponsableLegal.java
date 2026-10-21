package com.khalilmht.khalil.entities;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "responsables_legaux")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsableLegal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_complet", nullable = false, length = 150)
    private String nomComplet;

    @Column(name = "lien_parente", nullable = false, length = 50)
    private String lienParente; // Ex: "Père", "Mère", "Tuteur"

    @Column(nullable = false, length = 30)
    private String telephone;

    @Column(length = 150)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String adresse;

    @Column(name = "contact_urgence", nullable = false)
    @Builder.Default
    private Boolean contactUrgence = false;
}
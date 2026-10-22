package com.khalilmht.khalil.ui.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EleveDto {
    private Long id;
    private String matricule;
    private String nom;
    private String prenom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateNaissance;

    private String lieuNaissance;
    private String sexe; // Ajouté pour correspondre à la base de données
    private String email;
    private String statut;

    // Constructeur vide (nécessaire pour Jackson)
    public EleveDto() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getLieuNaissance() { return lieuNaissance; }
    public void setLieuNaissance(String lieuNaissance) { this.lieuNaissance = lieuNaissance; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
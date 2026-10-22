package com.khalilmht.khalil.controller.dto;

public class ClasseRequestDto {
    private String code;
    private String nom;
    private Long niveauId;
    private Integer capaciteMaximale;

    // Getters et Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Long getNiveauId() { return niveauId; }
    public void setNiveauId(Long niveauId) { this.niveauId = niveauId; }

    public Integer getCapaciteMaximale() { return capaciteMaximale; }
    public void setCapaciteMaximale(Integer capaciteMaximale) { this.capaciteMaximale = capaciteMaximale; }
}
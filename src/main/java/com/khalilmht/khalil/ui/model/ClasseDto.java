package com.khalilmht.khalil.ui.model;

public class ClasseDto {
    private Long id;
    private String code;
    private String nom;
    private NiveauDto niveau;
    private Integer capaciteMaximale;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public NiveauDto getNiveau() { return niveau; }
    public void setNiveau(NiveauDto niveau) { this.niveau = niveau; }

    public Integer getCapaciteMaximale() { return capaciteMaximale; }
    public void setCapaciteMaximale(Integer capaciteMaximale) { this.capaciteMaximale = capaciteMaximale; }
}
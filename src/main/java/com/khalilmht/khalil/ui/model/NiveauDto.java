package com.khalilmht.khalil.ui.model;

public class NiveauDto {
    private Long id;
    private String code;
    private String nom;

    // Getters et Setters obligatoires
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}
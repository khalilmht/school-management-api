package com.khalilmht.khalil.ui.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class NoteDto {
    private Long id;
    private Long evaluationId;
    private Long eleveId;
    private String eleveNomComplet; // Optionnel pour l'affichage
    private BigDecimal valeur;
    private String appreciation;
    private LocalDateTime dateSaisie;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEvaluationId() { return evaluationId; }
    public void setEvaluationId(Long evaluationId) { this.evaluationId = evaluationId; }

    public Long getEleveId() { return eleveId; }
    public void setEleveId(Long eleveId) { this.eleveId = eleveId; }

    public String getEleveNomComplet() { return eleveNomComplet; }
    public void setEleveNomComplet(String eleveNomComplet) { this.eleveNomComplet = eleveNomComplet; }

    public BigDecimal getValeur() { return valeur; }
    public void setValeur(BigDecimal valeur) { this.valeur = valeur; }

    public String getAppreciation() { return appreciation; }
    public void setAppreciation(String appreciation) { this.appreciation = appreciation; }

    public LocalDateTime getDateSaisie() { return dateSaisie; }
    public void setDateSaisie(LocalDateTime dateSaisie) { this.dateSaisie = dateSaisie; }
}
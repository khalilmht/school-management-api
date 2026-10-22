package com.khalilmht.khalil.dto;

import java.math.BigDecimal;

public class NoteRequestDto {
    private Long evaluationId;
    private Long eleveId;
    private BigDecimal valeur;
    private String appreciation;

    // Getters et Setters
    public Long getEvaluationId() { return evaluationId; }
    public void setEvaluationId(Long evaluationId) { this.evaluationId = evaluationId; }

    public Long getEleveId() { return eleveId; }
    public void setEleveId(Long eleveId) { this.eleveId = eleveId; }

    public BigDecimal getValeur() { return valeur; }
    public void setValeur(BigDecimal valeur) { this.valeur = valeur; }

    public String getAppreciation() { return appreciation; }
    public void setAppreciation(String appreciation) { this.appreciation = appreciation; }
}
package com.khalilmht.khalil.ui.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EvaluationDto {
    private Long id;
    private String intitule;
    private String typeEvaluation;
    private LocalDate dateEvaluation;
    private BigDecimal noteMaximale;
    private BigDecimal coefficient;
    private String periode;
    private Long classeMatiereId;
    private Boolean estValidee;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }

    public String getTypeEvaluation() { return typeEvaluation; }
    public void setTypeEvaluation(String typeEvaluation) { this.typeEvaluation = typeEvaluation; }

    public LocalDate getDateEvaluation() { return dateEvaluation; }
    public void setDateEvaluation(LocalDate dateEvaluation) { this.dateEvaluation = dateEvaluation; }

    public BigDecimal getNoteMaximale() { return noteMaximale; }
    public void setNoteMaximale(BigDecimal noteMaximale) { this.noteMaximale = noteMaximale; }

    public BigDecimal getCoefficient() { return coefficient; }
    public void setCoefficient(BigDecimal coefficient) { this.coefficient = coefficient; }

    public String getPeriode() { return periode; }
    public void setPeriode(String periode) { this.periode = periode; }

    public Long getClasseMatiereId() { return classeMatiereId; }
    public void setClasseMatiereId(Long classeMatiereId) { this.classeMatiereId = classeMatiereId; }

    public Boolean getEstValidee() { return estValidee; }
    public void setEstValidee(Boolean estValidee) { this.estValidee = estValidee; }
}
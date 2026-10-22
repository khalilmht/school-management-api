package com.khalilmht.khalil.dto;

import com.khalilmht.khalil.entities.Evaluation;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EvaluationRequestDto {
    private String intitule;
    private Evaluation.TypeEvaluation typeEvaluation;
    private LocalDate dateEvaluation;
    private BigDecimal noteMaximale;
    private BigDecimal coefficient;
    private String periode;
    private Long classeMatiereId;
    private Boolean estValidee;

    // Getters et Setters
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }

    public Evaluation.TypeEvaluation getTypeEvaluation() { return typeEvaluation; }
    public void setTypeEvaluation(Evaluation.TypeEvaluation typeEvaluation) { this.typeEvaluation = typeEvaluation; }

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
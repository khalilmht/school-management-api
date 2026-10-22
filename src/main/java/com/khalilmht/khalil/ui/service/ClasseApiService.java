package com.khalilmht.khalil.ui.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khalilmht.khalil.ui.model.ClasseDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class ClasseApiService {

    private static final String URL_API = "http://localhost:8080/api/v1/classes";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ClasseApiService() {
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<ClasseDto> recupererClasses() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_API))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<ClasseDto>>() {});
            }
        } catch (Exception e) {
            System.err.println("Erreur GET Classes : " + e.getMessage());
        }
        return Collections.emptyList();
    }

    // DTO interne pour l'envoi de la requête de création avec l'ID du niveau
    public static class ClasseRequestPayload {
        private String code;
        private String nom;
        private Long niveauId;
        private Integer capaciteMaximale;

        public ClasseRequestPayload(String code, String nom, Long niveauId, Integer capaciteMaximale) {
            this.code = code;
            this.nom = nom;
            this.niveauId = niveauId;
            this.capaciteMaximale = capaciteMaximale;
        }

        // Getters nécessaires pour Jackson
        public String getCode() { return code; }
        public String getNom() { return nom; }
        public Long getNiveauId() { return niveauId; }
        public Integer getCapaciteMaximale() { return capaciteMaximale; }
    }

    public String creerClasse(String code, String nom, Long niveauId, Integer capacite) {
        try {
            ClasseRequestPayload payload = new ClasseRequestPayload(code, nom, niveauId, capacite);
            String jsonBody = objectMapper.writeValueAsString(payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_API))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                return "SUCCES";
            } else {
                return response.body();
            }
        } catch (Exception e) {
            return "Erreur connexion : " + e.getMessage();
        }
    }
}
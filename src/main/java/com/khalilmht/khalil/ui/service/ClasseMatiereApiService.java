package com.khalilmht.khalil.ui.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khalilmht.khalil.ui.model.ClasseMatiereDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class ClasseMatiereApiService {

    private static final String URL_API = "http://localhost:8080/api/v1/classes-matieres";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ClasseMatiereApiService() {
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<ClasseMatiereDto> recupererClassesMatieres() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_API))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<ClasseMatiereDto>>() {});
            }
        } catch (Exception e) {
            System.err.println("Erreur GET Classes-Matieres : " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public String creerClasseMatiere(ClasseMatiereDto dto) {
        try {
            String jsonBody = objectMapper.writeValueAsString(dto);
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
            return "Erreur de connexion : " + e.getMessage();
        }
    }
}
package com.khalilmht.khalil.ui.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.khalilmht.khalil.ui.model.EleveDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class EleveApiService {

    private static final String URL_API = "http://localhost:8080/api/v1/eleves";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public EleveApiService() {
        // Configuration d'un HttpClient avec un timeout pour éviter de bloquer l'interface
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        this.objectMapper = new ObjectMapper();
        // Enregistrement du module pour gérer correctement les objets de type LocalDate
        this.objectMapper.registerModule(new JavaTimeModule());
        // Désactive l'écriture des dates sous forme de tableaux de nombres (timestamps)
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Ignore les propriétés inconnues reçues du JSON pour éviter les plantages
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public String creerEleve(EleveDto eleveDto) {
        try {
            String jsonBody = objectMapper.writeValueAsString(eleveDto);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_API))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code HTTP (POST): " + response.statusCode());
            System.out.println("Réponse Backend: " + response.body());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                return "SUCCES";
            } else {
                return response.body(); // Retourne le message d'erreur détaillé du backend
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la communication avec l'API (POST) : " + e.getMessage());
            return "Erreur de connexion au serveur : " + e.getMessage();
        }
    }

    public String modifierEleve(Long id, EleveDto eleveDto) {
        try {
            String jsonBody = objectMapper.writeValueAsString(eleveDto);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_API + "/" + id))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code HTTP (PUT): " + response.statusCode());
            System.out.println("Réponse Backend: " + response.body());

            if (response.statusCode() == 200) {
                return "SUCCES";
            } else {
                return response.body();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la communication avec l'API (PUT) : " + e.getMessage());
            return "Erreur de connexion au serveur : " + e.getMessage();
        }
    }

    public boolean supprimerEleve(Long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_API + "/" + id))
                    .header("Accept", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code HTTP (DELETE): " + response.statusCode());

            return response.statusCode() == 200;
        } catch (Exception e) {
            System.err.println("Erreur lors de la communication avec l'API (DELETE) : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<EleveDto> recupererEleves() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_API))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<EleveDto>>() {});
            } else {
                System.err.println("Erreur serveur lors de la récupération (GET). Code : " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la communication avec l'API (GET) : " + e.getMessage());
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
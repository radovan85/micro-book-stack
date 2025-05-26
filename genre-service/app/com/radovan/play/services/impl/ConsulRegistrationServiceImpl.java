package com.radovan.play.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.radovan.play.services.ConsulRegistrationService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class ConsulRegistrationServiceImpl implements ConsulRegistrationService {

    // Povezivanje na Consul preko Docker mreže
    private static final String CONSUL_SERVER_URL = "http://consul:8500/v1/agent/service/register";

    private WSClient wsClient;
    private ObjectMapper objectMapper;

    @Inject
    private void initialize(WSClient wsClient, ObjectMapper objectMapper) {
        this.wsClient = wsClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void registerService() {
        try {
            System.out.println("Starting service registration with Consul...");

            // Koristimo naziv servisa umesto IP adrese
            String appName = "genre-service";
            int port = 9000;

            // Kreiranje podataka za registraciju servisa
            Map<String, Object> serviceData = new HashMap<>();
            serviceData.put("ID", appName + "-" + port); // Jedinstven ID servisa
            serviceData.put("Name", appName); // Naziv servisa
            serviceData.put("Address", appName); // Mrežni naziv servisa, NE localhost!
            serviceData.put("Port", port); // Port servisa

            // Definisanje health check-a
            Map<String, String> check = new HashMap<>();
            check.put("HTTP", "http://genre-service:9000/api/health");
            check.put("Interval", "10s");
            check.put("Timeout", "1s");
            serviceData.put("Check", check);

            System.out.println("Service data: " + serviceData);

            // Konverzija u JSONNode
            JsonNode jsonPayload = objectMapper.valueToTree(serviceData);
            System.out.println("JSON payload: " + jsonPayload);

            // Slanje PUT zahteva ka Consul-u
            wsClient.url(CONSUL_SERVER_URL)
                    .addHeader("Content-Type", "application/json")
                    .setMethod("PUT")
                    .setBody(jsonPayload)
                    .execute()
                    .thenAccept(this::handleResponse)
                    .toCompletableFuture()
                    .join();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to register service with Consul", e);
        }
    }

    private void handleResponse(WSResponse response) {
        System.out.println("Consul response status: " + response.getStatus());
        if (response.getStatus() == 200 || response.getStatus() == 204) {
            System.out.println("Service registered successfully with Consul!");
        } else {
            System.err.println("Failed to register service with Consul: " + response.getStatusText());
            System.err.println("Response body: " + response.getBody());
        }
    }
}

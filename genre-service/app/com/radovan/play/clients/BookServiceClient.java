package com.radovan.play.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.radovan.play.utils.ServiceUrlProvider;
import jakarta.inject.Inject;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http;

public class BookServiceClient {

    private final WSClient ws;
    private final ServiceUrlProvider serviceUrlProvider;

    @Inject
    public BookServiceClient(WSClient ws, ServiceUrlProvider serviceUrlProvider) {
        this.ws = ws;
        this.serviceUrlProvider = serviceUrlProvider;
    }

    /**
     * Poziva book-service i briše sve knjige sa datim genreId.
     * Blokira dok ne dobije odgovor (možeš lako prebaciti u async varijantu).
     */
    public void deleteBooksByGenre(Http.Request request, Integer genreId) {
        String url = serviceUrlProvider.getBookServiceUrl() + "/api/books/deleteBooks/" + genreId;

        // Dohvati token iz requesta
        String token = request.getHeaders().get("Authorization").orElse("");

        ws.url(url)
                .addHeader("Authorization", token) // Sada prosleđujemo token!
                .delete()
                .toCompletableFuture()
                .join(); // Blokira dok se ne izvrši

        System.out.println("✅ Books with genre ID " + genreId + " deleted successfully!");
    }


    /**
     * Proverava da li postoji bar jedna knjiga sa datim genreId.
     * Vraća true ako postoji bar jedna knjiga sa tim žanrom, false ako nema nijedne.
     */
    public boolean hasBooksForGenre(Integer genreId) {
        String url = serviceUrlProvider.getBookServiceUrl() + "/api/books/selectedBooks/" + genreId;
        WSResponse response = ws.url(url)
                .get()
                .toCompletableFuture()
                .join();

        // Očekuje se da endpoint vrati listu knjiga (JSON array)
        // Ako je lista prazna, nema knjiga za taj žanr
        return response.getStatus() == 200 && !response.asJson().isEmpty();
    }

    public JsonNode getAllBooksByGenre(Http.Request request, Integer genreId) {
        String url = serviceUrlProvider.getBookServiceUrl() + "/api/books/selectedBooks/" + genreId;

        // Proveravamo da li je token prisutan
        String token = request.headers().get("Authorization").orElse("");

        WSResponse response = ws.url(url)
                .addHeader("Authorization", token) // Sada prosleđujemo token!
                .get()
                .toCompletableFuture()
                .join();

        // Proveravamo da li je zahtev uspešan
        if (response.getStatus() == 200) {
            return response.asJson(); // Vraćamo listu knjiga u JSON formatu
        } else {
            return Json.newArray(); // Vraćamo prazan JSON array ako nema knjiga
        }
    }


}
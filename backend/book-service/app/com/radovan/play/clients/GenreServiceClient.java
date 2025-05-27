package com.radovan.play.clients;

import com.radovan.play.utils.ServiceUrlProvider;
import jakarta.inject.Inject;
import play.libs.ws.WSClient;
import play.mvc.Http;

import java.util.concurrent.CompletionStage;

public class GenreServiceClient {

    private WSClient ws;
    private ServiceUrlProvider serviceUrlProvider;

    @Inject
    private void initialize(WSClient ws, ServiceUrlProvider serviceUrlProvider) {
        this.ws = ws;
        this.serviceUrlProvider = serviceUrlProvider;
    }

    public CompletionStage<Boolean> genreExists(Http.Request request, Integer genreId) {
        String token = request.headers().get("Authorization").orElse("");

        String url = serviceUrlProvider.getGenreServiceUrl() + "/api/genres/" + genreId;
        return ws.url(url)
                .addHeader("Authorization", token) // Sada prosleđujemo token!
                .get()
                .thenApply(response -> response.getStatus() == 200);
    }






}

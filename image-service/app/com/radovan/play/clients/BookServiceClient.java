package com.radovan.play.clients;

import com.radovan.play.utils.ServiceUrlProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import play.libs.ws.WSClient;
import play.mvc.Http;

import java.util.concurrent.CompletionStage;

@Singleton
public class BookServiceClient {

    private final WSClient ws;
    private final ServiceUrlProvider serviceUrlProvider;

    @Inject
    public BookServiceClient(WSClient ws, ServiceUrlProvider serviceUrlProvider) {
        this.ws = ws;
        this.serviceUrlProvider = serviceUrlProvider;
    }

    public CompletionStage<Boolean> bookExists(Http.Request request, Integer bookId) {
        String url = serviceUrlProvider.getBookServiceUrl() + "/api/books/" + bookId;

        // Dohvati token iz requesta
        String token = request.headers().get("Authorization").orElse("");

        return ws.url(url)
                .addHeader("Authorization", token) // Sada prosleđujemo token!
                .get()
                .thenApply(response -> {
                    return response.getStatus() == 200;
                });
    }

}

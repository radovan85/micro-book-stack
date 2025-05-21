package com.radovan.play.utils;

import com.radovan.play.services.BookService;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.Duration;

@Singleton
public class NatsSubscriber {

    private final BookService bookService;

    @Inject  // Dodajemo @Inject anotaciju
    public NatsSubscriber(BookService bookService) {
        this.bookService = bookService;
        startListening();
    }

    private void startListening() {
        try {
            Connection nc = Nats.connect("nats://localhost:4222");

            Dispatcher d = nc.createDispatcher((msg) -> {
                String message = new String(msg.getData());
                System.out.println("*** NATS message received: " + message);

                try {
                    int bookId = Integer.parseInt(message.split("\"bookId\":")[1].split(",")[0]);
                    int imageId = Integer.parseInt(message.split("\"imageId\":")[1].split("}")[0]);

                    bookService.addImage(bookId, imageId);
                } catch (Exception e) {
                    System.err.println("*** Error processing NATS message!");
                    e.printStackTrace();
                }
            });

            d.subscribe("book.image.uploaded");
            System.out.println("*** NATS Subscriber has been initialized");

        } catch (Exception e) {
            System.err.println("*** Error accessing NATS server!");
            e.printStackTrace();
        }
    }
}
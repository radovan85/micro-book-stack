package com.radovan.play.utils;

import com.radovan.play.services.BookImageService;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class NatsSubscriber {

    private final BookImageService imageService;
    private Connection nc;

    @Inject
    public NatsSubscriber(BookImageService imageService) {
        this.imageService = imageService;
        startListening();
    }

    private void startListening() {
        try {
            Connection nc = Nats.connect("nats://nats:4222");
            Dispatcher dispatcher = nc.createDispatcher((msg) -> {
                String message = new String(msg.getData());
                System.out.println("*** Message received for deleting the image: " + message);

                try {
                    int imageId = Integer.parseInt(message.split("\"imageId\":")[1].split("}")[0]);

                    imageService.deleteImage(imageId);  // Deleting the image in image-service
                    System.out.println("*** Image with ID " + imageId + " has been removed!");
                } catch (Exception e) {
                    System.err.println("*** ERROR PROCESSING MESSAGE FOR IMAGE DELETION!");
                    e.printStackTrace();
                }
            });

            dispatcher.subscribe("book.image.deleted");
            System.out.println("*** NATS Subscriber started and listening on 'book.image.deleted'");

        } catch (Exception e) {
            System.err.println("*** ERROR CONNECTING TO NATS!");
            e.printStackTrace();
        }
    }
}

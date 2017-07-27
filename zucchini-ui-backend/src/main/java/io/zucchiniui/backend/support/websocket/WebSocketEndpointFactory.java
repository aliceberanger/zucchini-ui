package io.zucchiniui.backend.support.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Arrays;
import java.util.function.Supplier;

public class WebSocketEndpointFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEndpointFactory.class);

    public WebSocketEndpointFactory() {
    }

    public <T> ServerEndpointConfig createEndpoint(Class<T> clazz, Supplier<T> endpointSupplier) {
        final ServerEndpoint endpointAnnotation = clazz.getAnnotation(ServerEndpoint.class);
        if (endpointAnnotation == null) {
            throw new IllegalArgumentException(clazz + " has no @ServerEndpoint implementation");
        }

        LOGGER.info("Creating endpoint for {} at path {}", clazz, endpointAnnotation.value());

        return ServerEndpointConfig.Builder.create(clazz, endpointAnnotation.value())
            .configurator(new ServerEndpointConfig.Configurator() {

                @Override
                public <X> X getEndpointInstance(final Class<X> endpointClass) throws InstantiationException {
                    // Use Spring to create the endpoint instance
                    return endpointClass.cast(endpointSupplier.get());
                }

            })
            .encoders(Arrays.asList(endpointAnnotation.encoders()))
            .decoders(Arrays.asList(endpointAnnotation.decoders()))
            .subprotocols(Arrays.asList(endpointAnnotation.subprotocols()))
            .build();
    }

}

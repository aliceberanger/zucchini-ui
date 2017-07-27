package io.zucchiniui.backend.support.websocket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.util.function.Supplier;

public class WebSocketServletContextListener<T> implements ServletContextListener {

    private final WebSocketEndpointFactory webSocketEndpointFactory;

    private final Class<T> clazz;

    private final Supplier<T> endpointSupplier;

    public WebSocketServletContextListener(
        WebSocketEndpointFactory webSocketEndpointFactory,
        Class<T> clazz,
        Supplier<T> endpointSupplier
    ) {
        this.webSocketEndpointFactory = webSocketEndpointFactory;
        this.clazz = clazz;
        this.endpointSupplier = endpointSupplier;
    }

    @Override
    public void contextInitialized(final ServletContextEvent servletContextEvent) {
        // A servlet listener must be used to register endpoints. Otherwise, ServerContainer is not defined.
        final ServerContainer serverContainer = (ServerContainer) servletContextEvent.getServletContext().getAttribute(ServerContainer.class.getName());
        if (serverContainer == null) {
            throw new IllegalStateException("WebSocket ServerContainer doesn't exist");
        }

        try {
            final ServerEndpointConfig endpointConfig = webSocketEndpointFactory.createEndpoint(clazz, endpointSupplier);
            serverContainer.addEndpoint(endpointConfig);
        } catch (DeploymentException e) {
            throw new IllegalStateException("Failed to deploy WebSocket endpoint " + clazz, e);
        }
    }

    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {

    }

}

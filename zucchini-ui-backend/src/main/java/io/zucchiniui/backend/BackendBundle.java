package io.zucchiniui.backend;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.zucchiniui.backend.presence.websocket.PresenceEndpoint;
import io.zucchiniui.backend.support.ddd.rest.EntityNotFoundExceptionMapper;
import io.zucchiniui.backend.support.websocket.WebSocketEnablerBundle;
import io.zucchiniui.backend.support.websocket.WebSocketEndpointFactory;
import io.zucchiniui.backend.support.websocket.WebSocketServletContextListener;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class BackendBundle implements ConfiguredBundle<BackendConfiguration> {

    @Override
    public void initialize(final Bootstrap<?> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
            bootstrap.getConfigurationSourceProvider(),
            new EnvironmentVariableSubstitutor(false)
        ));

        // Configure Jackson mapper
        bootstrap.getObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Enable WebSockets for Jetty
        bootstrap.addBundle(new WebSocketEnablerBundle());

        // Register Spring context
        // applicationContext.register(BackendSpringConfig.class);
        // bootstrap.addBundle(new SpringBundle(applicationContext));

    }

    @Override
    public void run(final BackendConfiguration configuration, final Environment environment) throws Exception {
        final FilterRegistration.Dynamic crossOriginFilterRegistration = environment.servlets()
            .addFilter("cors-filter", CrossOriginFilter.class);
        crossOriginFilterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
        crossOriginFilterRegistration.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,PUT,PATCH,DELETE");

        configuration.getMetrics().configure(environment.lifecycle(), environment.metrics());

        environment.jersey().register(new EntityNotFoundExceptionMapper());

        final ApplicationComponent component = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(configuration, environment))
            .build();

        environment.jersey().register(component.testRunResource());
        environment.jersey().register(component.featureResource());
        environment.jersey().register(component.scenarioResource());

        final WebSocketEndpointFactory webSocketEndpointFactory = new WebSocketEndpointFactory();
        environment.getApplicationContext().addEventListener(new WebSocketServletContextListener<>(
            webSocketEndpointFactory,
            PresenceEndpoint.class,
            () -> component.requestScopeComponent().presenceEndpoint()
        ));
    }

}

package io.zucchiniui.backend.auth.springconfig;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.setup.Environment;
import io.zucchiniui.backend.BackendConfiguration;
import io.zucchiniui.backend.auth.domain.User;
import io.zucchiniui.backend.auth.domain.UserRepository;
import io.zucchiniui.backend.auth.rest.AuthResource;
import io.zucchiniui.backend.auth.rest.JWTAuthFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Configuration
public class AuthSpringConfig {

    @Autowired
    private BackendConfiguration configuration;

    @Autowired
    private Environment dropwizardEnvironment;

    @Bean
    public UserRepository userRepository() {
        return configuration.getUserRepository().create();
    }

    @Bean
    public AuthFilter jwtAuthFilter() {
        return new JWTAuthFilter.Builder<User>()
            .setJWTVerifier(configuration.getAuth().createJWTVerifier())
            .setAuthenticator(credentials -> Optional.of(User.fromJWTClaims(credentials)))
            .setAuthorizer(User::hasRole)
            .buildAuthFilter();
    }

    @Bean
    public AuthResource<User> authResource() {
        return new AuthResource<>(
            configuration.getAuth().createJWTSigner(),
            configuration.getAuth().createJWTSignerOptions(),
            credentials -> {
                final String name = credentials.getName();
                final String password = credentials.getPassword();
                return userRepository().findByNameAndPassword(name, password);
            },
            User::toJWTClaims
        );
    }

    @PostConstruct
    public void init() {
        dropwizardEnvironment.jersey().register(new AuthDynamicFeature(jwtAuthFilter()));
        dropwizardEnvironment.jersey().register(RolesAllowedDynamicFeature.class);
        dropwizardEnvironment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }


}

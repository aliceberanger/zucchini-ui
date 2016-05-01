package io.zucchiniui.backend;

import io.dropwizard.Configuration;
import io.dropwizard.metrics.MetricsFactory;
import io.zucchiniui.backend.auth.config.AuthFactory;
import io.zucchiniui.backend.auth.config.UserRepositoryFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class BackendConfiguration extends Configuration {

    @NotNull
    private String mongoUri;

    @Valid
    private final MetricsFactory metrics = new MetricsFactory();

    @Valid
    private final AuthFactory auth = new AuthFactory();

    @NotNull
    @Valid
    private UserRepositoryFactory userRepository;

    public String getMongoUri() {
        return mongoUri;
    }

    public void setMongoUri(final String mongoUri) {
        this.mongoUri = mongoUri;
    }

    public MetricsFactory getMetrics() {
        return metrics;
    }

    public AuthFactory getAuth() {
        return auth;
    }

    public UserRepositoryFactory getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(final UserRepositoryFactory userRepository) {
        this.userRepository = userRepository;
    }

}

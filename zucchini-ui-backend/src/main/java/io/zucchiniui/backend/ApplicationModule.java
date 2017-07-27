package io.zucchiniui.backend;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;
import io.dropwizard.setup.Environment;
import io.zucchiniui.backend.feature.FeatureModule;
import io.zucchiniui.backend.scenario.ScenarioModule;
import io.zucchiniui.backend.support.morphia.MorphiaDatastoreBuilder;
import io.zucchiniui.backend.testrun.TestRunModule;
import org.mongodb.morphia.Datastore;

import javax.inject.Named;
import javax.inject.Singleton;

@Module(includes = {
    TestRunModule.class,
    ScenarioModule.class,
    FeatureModule.class
})
public class ApplicationModule {

    private final BackendConfiguration configuration;

    private final Environment dropwizardEnvironment;

    public ApplicationModule(BackendConfiguration configuration, Environment dropwizardEnvironment) {
        this.configuration = configuration;
        this.dropwizardEnvironment = dropwizardEnvironment;
    }

    @Provides
    @Singleton
    public Datastore datastore() {
        return new MorphiaDatastoreBuilder(dropwizardEnvironment)
            .withUri(configuration.getMongoUri())
            .build("mongo");
    }

    @Provides
    @Singleton
    public ObjectMapper objectMapper() {
        return dropwizardEnvironment.getObjectMapper();
    }

    @Provides
    @Singleton
    @Named("reportObjectMapper")
    public ObjectMapper reportObjectMapper() {
        return dropwizardEnvironment.getObjectMapper()
            .copy()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

}

package io.testscucumber.backend.feature.domainimpl;

import io.testscucumber.backend.feature.domain.Feature;
import io.testscucumber.backend.feature.domain.FeatureFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.UUID;

@Component
class FeatureFactoryImpl implements FeatureFactory {

    @Override
    public Feature create(final String testRunId) {
        final Feature feature = new Feature();
        feature.setId(UUID.randomUUID().toString());
        feature.setTestRunId(testRunId);

        final ZonedDateTime now = ZonedDateTime.now();
        feature.setCreatedAt(now);
        feature.setModifiedAt(now);

        return feature;
    }

}

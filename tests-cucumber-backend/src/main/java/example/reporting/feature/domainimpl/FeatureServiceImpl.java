package example.reporting.feature.domainimpl;

import example.reporting.feature.domain.Feature;
import example.reporting.feature.domain.FeatureRepository;
import example.reporting.feature.domain.FeatureService;
import example.reporting.feature.domain.FeatureStats;
import example.reporting.scenario.domain.Scenario;
import example.reporting.scenario.domain.ScenarioRepository;
import example.reporting.scenario.domain.ScenarioStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;

    private final ScenarioRepository scenarioRepository;

    @Autowired
    public FeatureServiceImpl(final FeatureRepository featureRepository, final ScenarioRepository scenarioRepository) {
        this.featureRepository = featureRepository;
        this.scenarioRepository = scenarioRepository;
    }

    @Override
    public FeatureStats computeStats(final String featureId) {
        final Map<ScenarioStatus, Integer> statsByStatus = new EnumMap<>(ScenarioStatus.class);
        for (final ScenarioStatus status : ScenarioStatus.values()) {
            statsByStatus.put(status, 0);
        }

        final List<Scenario> scenarii = scenarioRepository
            .query()
            .withFeatureId(featureId)
            .find();

        for (final Scenario scenario : scenarii) {
            statsByStatus.compute(scenario.getStatus(), (key, count) -> count + 1);
        }
        return new FeatureStats(scenarii.size(), statsByStatus);
    }

    @Override
    public void deleteByTestRunId(final String testRunId) {
        scenarioRepository.deleteByTestRunId(testRunId);
        featureRepository.deleteByTestRunId(testRunId);
    }

    @Override
    public void deleteById(final String featureId) {
        final Feature feature = featureRepository.getById(featureId);

        scenarioRepository.deleteByFeatureId(featureId);

        featureRepository.delete(feature);
    }

}

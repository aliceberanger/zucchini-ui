package io.zucchiniui.backend;

import dagger.Component;
import io.zucchiniui.backend.feature.rest.FeatureResource;
import io.zucchiniui.backend.scenario.rest.ScenarioResource;
import io.zucchiniui.backend.testrun.rest.TestRunResource;

import javax.inject.Singleton;

@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {

    TestRunResource testRunResource();

    FeatureResource featureResource();

    ScenarioResource scenarioResource();

    RequestScopeComponent requestScopeComponent();

}

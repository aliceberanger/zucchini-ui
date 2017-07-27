package io.zucchiniui.backend.scenario.domainimpl;

import dagger.Binds;
import dagger.Module;
import io.zucchiniui.backend.scenario.domain.ScenarioRepository;
import io.zucchiniui.backend.scenario.domain.ScenarioService;

@Module
public abstract class ScenarioDomainModule {

    @Binds
    public abstract ScenarioRepository scenarioRepository(ScenarioRepositoryImpl scenarioRepositoryImpl);

    @Binds
    public abstract ScenarioService scenarioService(ScenarioServiceImpl scenarioServiceImpl);

}

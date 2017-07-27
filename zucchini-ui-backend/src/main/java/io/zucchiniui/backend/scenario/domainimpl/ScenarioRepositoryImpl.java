package io.zucchiniui.backend.scenario.domainimpl;

import io.zucchiniui.backend.scenario.dao.ScenarioDAO;
import io.zucchiniui.backend.scenario.domain.Scenario;
import io.zucchiniui.backend.scenario.domain.ScenarioQuery;
import io.zucchiniui.backend.scenario.domain.ScenarioRepository;
import io.zucchiniui.backend.support.ddd.morphia.MorphiaQueriableRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class ScenarioRepositoryImpl extends MorphiaQueriableRepository<Scenario, String, ScenarioQuery> implements ScenarioRepository {

    @Inject
    public ScenarioRepositoryImpl(final ScenarioDAO dao) {
        super(dao);
    }

}

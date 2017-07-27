package io.zucchiniui.backend.scenario.dao;

import io.zucchiniui.backend.scenario.domain.Scenario;
import io.zucchiniui.backend.scenario.domain.ScenarioQuery;
import io.zucchiniui.backend.support.ddd.morphia.MorphiaTypedQueryDAO;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Consumer;

@Singleton
public class ScenarioDAO extends MorphiaTypedQueryDAO<Scenario, String, ScenarioQuery> {

    @Inject
    public ScenarioDAO(final Datastore ds) {
        super(ds);
    }

    @Override
    public Query<Scenario> prepareTypedQuery(final Consumer<? super ScenarioQuery> preparator) {
        final ScenarioQueryImpl typedQuery = new ScenarioQueryImpl(createQuery());
        preparator.accept(typedQuery);
        return typedQuery.morphiaQuery();
    }

}

package io.zucchiniui.backend.feature.dao;

import io.zucchiniui.backend.feature.domain.Feature;
import io.zucchiniui.backend.feature.domain.FeatureQuery;
import io.zucchiniui.backend.support.ddd.morphia.MorphiaTypedQueryDAO;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Consumer;

@Singleton
public class FeatureDAO extends MorphiaTypedQueryDAO<Feature, String, FeatureQuery> {

    @Inject
    public FeatureDAO(final Datastore ds) {
        super(ds);
    }

    @Override
    public Query<Feature> prepareTypedQuery(final Consumer<? super FeatureQuery> preparator) {
        final FeatureQueryImpl typedQuery = new FeatureQueryImpl(createQuery());
        preparator.accept(typedQuery);
        return typedQuery.morphiaQuery();
    }

}

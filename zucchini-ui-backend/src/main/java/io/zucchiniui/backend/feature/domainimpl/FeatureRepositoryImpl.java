package io.zucchiniui.backend.feature.domainimpl;

import io.zucchiniui.backend.feature.dao.FeatureDAO;
import io.zucchiniui.backend.feature.domain.Feature;
import io.zucchiniui.backend.feature.domain.FeatureQuery;
import io.zucchiniui.backend.feature.domain.FeatureRepository;
import io.zucchiniui.backend.support.ddd.morphia.MorphiaQueriableRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class FeatureRepositoryImpl extends MorphiaQueriableRepository<Feature, String, FeatureQuery> implements FeatureRepository {

    @Inject
    public FeatureRepositoryImpl(final FeatureDAO dao) {
        super(dao);
    }

}

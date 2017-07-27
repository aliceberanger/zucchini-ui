package io.zucchiniui.backend.feature.domainimpl;

import dagger.Binds;
import dagger.Module;
import io.zucchiniui.backend.feature.domain.FeatureRepository;
import io.zucchiniui.backend.feature.domain.FeatureService;

@Module
public abstract class FeatureDomainModule {

    @Binds
    public abstract FeatureRepository featureRepository(FeatureRepositoryImpl featureRepositoryImpl);

    @Binds
    public abstract FeatureService featureService(FeatureServiceImpl featureServiceImpl);

}

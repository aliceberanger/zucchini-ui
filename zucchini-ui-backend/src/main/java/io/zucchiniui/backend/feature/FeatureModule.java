package io.zucchiniui.backend.feature;

import dagger.Module;
import io.zucchiniui.backend.feature.domainimpl.FeatureDomainModule;

@Module(includes = {
    FeatureDomainModule.class
})
public class FeatureModule {
}

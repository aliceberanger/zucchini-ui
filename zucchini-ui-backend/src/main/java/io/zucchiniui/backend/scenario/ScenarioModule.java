package io.zucchiniui.backend.scenario;

import dagger.Module;
import io.zucchiniui.backend.comment.CommentModule;
import io.zucchiniui.backend.scenario.domainimpl.ScenarioDomainModule;

@Module(includes = {
    ScenarioDomainModule.class,
    CommentModule.class
})
public class ScenarioModule {
}

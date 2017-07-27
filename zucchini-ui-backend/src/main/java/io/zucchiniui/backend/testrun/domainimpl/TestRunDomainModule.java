package io.zucchiniui.backend.testrun.domainimpl;

import dagger.Binds;
import dagger.Module;
import io.zucchiniui.backend.testrun.domain.TestRunRepository;
import io.zucchiniui.backend.testrun.domain.TestRunService;

@Module
public abstract class TestRunDomainModule {

    @Binds
    public abstract TestRunRepository testRunRepository(TestRunRepositoryImpl testRunRepositoryImpl);

    @Binds
    public abstract TestRunService testRunService(TestRunServiceImpl testRunServiceImpl);

}

package io.zucchiniui.backend.testrun.domainimpl;

import io.zucchiniui.backend.support.ddd.morphia.MorphiaQueriableRepository;
import io.zucchiniui.backend.testrun.dao.TestRunDAO;
import io.zucchiniui.backend.testrun.domain.TestRun;
import io.zucchiniui.backend.testrun.domain.TestRunQuery;
import io.zucchiniui.backend.testrun.domain.TestRunRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class TestRunRepositoryImpl extends MorphiaQueriableRepository<TestRun, String, TestRunQuery> implements TestRunRepository {

    @Inject
    public TestRunRepositoryImpl(final TestRunDAO dao) {
        super(dao);
    }

}

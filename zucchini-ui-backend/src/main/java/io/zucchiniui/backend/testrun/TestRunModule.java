package io.zucchiniui.backend.testrun;

import dagger.Module;
import io.zucchiniui.backend.reportconverter.ReportConverterModule;
import io.zucchiniui.backend.testrun.domainimpl.TestRunDomainModule;

@Module(includes = {
    TestRunDomainModule.class,
    ReportConverterModule.class
})
public class TestRunModule {
}

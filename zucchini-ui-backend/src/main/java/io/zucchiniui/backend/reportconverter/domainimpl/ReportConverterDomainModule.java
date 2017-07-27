package io.zucchiniui.backend.reportconverter.domainimpl;

import dagger.Binds;
import dagger.Module;
import io.zucchiniui.backend.reportconverter.domain.ReportConverterService;

@Module
public abstract class ReportConverterDomainModule {

    @Binds
    public abstract ReportConverterService reportConverterService(ReportConverterServiceImpl reportConverterServiceImpl);

}

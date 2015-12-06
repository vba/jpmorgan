package be.bartel.sssm;

import com.google.inject.AbstractModule;

final class GuiceConfig extends AbstractModule {
    @Override
    protected void configure() {
        bind(DividendCalculationService.class).to(DividendCalculationServiceImpl.class);
        bind(TradeStorageService.class).to(TradeStorageServiceImpl.class);
        bind(WeightedVolumeCalculationService.class).to(WeightedVolumeCalculationServiceImpl.class);
        bind(GBCECalculationService.class).to(GBCECalculationServiceImpl.class);
        bind(NthRootHelper.class).to(NthRootHelperImpl.class);
    }
}

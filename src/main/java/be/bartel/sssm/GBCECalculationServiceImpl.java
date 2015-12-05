package be.bartel.sssm;

import com.google.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

public class GBCECalculationServiceImpl implements GBCECalculationService {
    private final TradeStorageService tradeStorageService;
    private final NthRootHelper nthRootHelper;

    @Override
    public BigDecimal calculateAllSharedIndex() {
        final List<BigDecimal> prices = tradeStorageService.getAllTrades().stream().map(Trade::getTradedPrice).collect(toList());
        final BigDecimal reduced = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return this.nthRootHelper.calculate(prices.size(), reduced);
    }

    @Inject
    public GBCECalculationServiceImpl(TradeStorageService tradeStorageService, NthRootHelper nthRootHelper) {
        checkArgument(tradeStorageService != null, "Trade storage service cannot be a null");
        checkArgument(nthRootHelper != null, "Nth root helper cannot be a null");

        this.nthRootHelper = nthRootHelper;
        this.tradeStorageService = tradeStorageService;
    }
}

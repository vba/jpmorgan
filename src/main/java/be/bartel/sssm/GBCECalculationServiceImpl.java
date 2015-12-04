package be.bartel.sssm;

import com.google.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

public class GBCECalculationServiceImpl implements GBCECalculationService {
    private final TradeStorageService tradeStorageService;

    @Override
    public BigDecimal calculateAllSharedIndex() {
        final List<BigDecimal> prices = tradeStorageService.getAllTrades().stream().map(x -> x.getTradedPrice()).collect(toList());
        final BigDecimal reduced = prices.stream().reduce(BigDecimal.ZERO, (accumulated, x) -> accumulated.add(x));
        return NthRoot.calculate(prices.size(), reduced);
    }

    @Inject
    public GBCECalculationServiceImpl(TradeStorageService tradeStorageService) {
        checkArgument(tradeStorageService != null, "Trade storage service cannot be a null");
        this.tradeStorageService = tradeStorageService;
    }
}

package be.bartel.sssm;

import com.google.inject.Inject;

import java.math.BigDecimal;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;

public class WeightedVolumeCalculationServiceImpl implements WeightedVolumeCalculationService {

    private final int SCALE = 2;
    private final TradeStorageService tradeStorageService;

    @Override
    public BigDecimal calculate(Stock stock) {
        final Set<Trade> lastTrades = tradeStorageService.getLastTrades(stock);
        if (lastTrades.size() == 0) {
            return ZERO;
        }

        final BigDecimal denominator = lastTrades.stream()
            .map(x -> new BigDecimal(x.getShareQuantity()))
            .reduce(ZERO, BigDecimal::add);

        return lastTrades.stream()
            .map(x -> x.getTradedPrice().multiply(new BigDecimal(x.getShareQuantity())))
            .reduce(ZERO, BigDecimal::add)
            .divide(denominator, SCALE, HALF_EVEN);
    }

    @Inject
    public WeightedVolumeCalculationServiceImpl(TradeStorageService tradeStorageService) {
        checkArgument(tradeStorageService != null, "Trade storage service cannot be a null");

        this.tradeStorageService = tradeStorageService;
    }
}

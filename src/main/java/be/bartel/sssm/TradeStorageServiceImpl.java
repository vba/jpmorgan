package be.bartel.sssm;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.stream.Collectors.toList;

@Singleton
public class TradeStorageServiceImpl implements TradeStorageService {

    private List<Trade> trades = ImmutableList.of();
    private final static long DEFAULT_INTERVAL = 15;

    @Override
    public void record(Trade trade) {
        trades = ImmutableList.<Trade>builder()
            .addAll(trades)
            .add(trade)
            .build();
    }

    @Override
    public List<Trade> getLastTrades(Stock stock) {
        return getLastTrades(DEFAULT_INTERVAL, stock);
    }

    @Override
    public List<Trade> getLastTrades(long minutes, Stock stock) {
        final LocalDateTime now = LocalDateTime.now();
        return trades.stream()
            .filter(x -> stock.equals(x.getStock()) && x.getCreationTime().until(now, MINUTES) <= minutes)
            .collect(toList());
    }

    @Override
    public List<Trade> getAllTrades() {
        return trades;
    }

    @Inject
    public TradeStorageServiceImpl() {
    }

    @VisibleForTesting
    public TradeStorageServiceImpl(ImmutableList<Trade> trades) {
        this.trades = trades;
    }
}

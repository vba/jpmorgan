package be.bartel.sssm;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static be.bartel.sssm.Trade.createBuyTrade;
import static be.bartel.sssm.Trade.createSellTrade;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Singleton
public class TradeStorageServiceImpl implements TradeStorageService {

    private Set<Trade> trades = ImmutableSet.of();
    private final static long DEFAULT_INTERVAL = 15;

    @Override
    public void sell(Stock stock, Instant timestamp, long shareQuantity, BigDecimal tradedPrice) {
        record(createSellTrade(stock, timestamp, shareQuantity, tradedPrice));
    }
    @Override
    public void buy(Stock stock, Instant timestamp, long shareQuantity, BigDecimal tradedPrice) {
        record(createBuyTrade(stock, timestamp, shareQuantity, tradedPrice));
    }

    @Override
    public void record(Trade trade) {
        trades = ImmutableSet.<Trade>builder()
            .addAll(trades)
            .add(trade)
            .build();
    }

    @Override
    public Set<Trade> getLastTrades(Stock stock) {
        return getLastTrades(DEFAULT_INTERVAL, stock);
    }

    @Override
    public Set<Trade> getLastTrades(long minutes, Stock stock) {
        final LocalDateTime now = LocalDateTime.now();
        return trades.stream()
            .filter(x -> stock.equals(x.getStock()) && x.getCreationTime().until(now, MINUTES) <= minutes)
            .collect(toSet());
    }

    @Override
    public Set<Trade> getAllTrades() {
        return trades;
    }

    @Inject
    public TradeStorageServiceImpl() {
    }

    @VisibleForTesting
    public TradeStorageServiceImpl(ImmutableSet<Trade> trades) {
        this.trades = trades;
    }
}

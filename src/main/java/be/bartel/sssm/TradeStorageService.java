package be.bartel.sssm;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public interface TradeStorageService {
    void sell(Stock stock, Instant timestamp, long shareQuantity, BigDecimal tradedPrice);

    void buy(Stock stock, Instant timestamp, long shareQuantity, BigDecimal tradedPrice);

    void record(Trade trade);

    Set<Trade> getLastTrades(Stock stock);

    Set<Trade> getLastTrades(long minutes, Stock stock);

    Set<Trade> getAllTrades();
}

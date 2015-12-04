package be.bartel.sssm;

import java.util.List;

public interface TradeStorageService {
    void record(Trade trade);

    List<Trade> getLastTrades(Stock stock);

    List<Trade> getLastTrades(long minutes, Stock stock);

    List<Trade> getAllTrades();
}

package be.bartel.sssm;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Trade {
    private final Stock stock;
    private final TradeType tradeType;
    private final long shareQuantity;
    private final BigDecimal tradedPrice;
    private final LocalDateTime creationTime;

    private Trade(Stock stock, TradeType tradeType, Instant timestamp, long shareQuantity, BigDecimal tradedPrice) {
        this.stock = stock;
        this.tradeType = tradeType;
        this.shareQuantity = shareQuantity;
        this.tradedPrice = tradedPrice;
        this.creationTime = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
    }

    public static Trade createSellTrade(Stock stock, Instant timestamp, long shareQuantity, BigDecimal tradedPrice) {
        return new Trade(stock, TradeType.SELL, timestamp, shareQuantity, tradedPrice);
    }

    public static Trade createBuyTrade(Stock stock, Instant timestamp, long shareQuantity, BigDecimal tradedPrice) {
        return new Trade(stock, TradeType.BUY, timestamp, shareQuantity, tradedPrice);
    }

    public Stock getStock() {
        return stock;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public long getShareQuantity() {
        return shareQuantity;
    }

    public BigDecimal getTradedPrice() {
        return tradedPrice;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    enum TradeType {
        BUY,
        SELL
    }
}

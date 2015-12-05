package be.bartel.sssm;

import com.google.common.base.Objects;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.google.common.base.Preconditions.checkArgument;

public class Trade {
    private final Stock stock;
    private final TradeType tradeType;
    private final long shareQuantity;
    private final BigDecimal tradedPrice;
    private final LocalDateTime creationTime;

    private Trade(Stock stock,
                  TradeType tradeType,
                  Instant timestamp,
                  long shareQuantity,
                  BigDecimal tradedPrice) {

        checkArgument(stock != null, "stock cannot be a null");
        checkArgument(tradeType != null, "trade type cannot be a null");
        checkArgument(timestamp != null, "timestamp cannot be a null");
        checkArgument(tradedPrice != null, "traded price cannot be a null");

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return shareQuantity == trade.shareQuantity &&
            Objects.equal(stock, trade.stock) &&
            tradeType == trade.tradeType &&
            Objects.equal(tradedPrice, trade.tradedPrice) &&
            Objects.equal(creationTime, trade.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stock, tradeType, shareQuantity, tradedPrice, creationTime);
    }

    @Override
    public String toString() {
        return "Trade{" +
            "stock=" + stock.getSymbol() +
            ", tradeType=" + tradeType +
            ", shareQuantity=" + shareQuantity +
            ", tradedPrice=" + tradedPrice +
            ", creationTime=" + creationTime +
            '}';
    }

    enum TradeType {
        BUY,
        SELL
    }
}

package be.bartel.sssm;

import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static be.bartel.sssm.Stock.StockType.COMMON;
import static be.bartel.sssm.Stock.StockType.PREFERRED;

final class Stock {

    static final Stock TEA = new Stock("TEA", COMMON, new BigDecimal("0"), new BigDecimal("100"));
    static final Stock POP = new Stock("POP", COMMON, new BigDecimal("8"), new BigDecimal("100"));
    static final Stock ALE = new Stock("ALE", COMMON, new BigDecimal("23"), new BigDecimal("60"));
    static final Stock JOE = new Stock("JOE", COMMON, new BigDecimal("13"), new BigDecimal("250"));
    static final Stock GIN = new Stock("GIN", PREFERRED, new BigDecimal("8"), Optional.of(new BigDecimal("2")), new BigDecimal("100"));

    private static final Map<String, Stock> hashedStocks = new ImmutableMap.Builder<String, Stock>()
        .put(TEA.symbol, TEA)
        .put(POP.symbol, POP)
        .put(ALE.symbol, ALE)
        .put(JOE.symbol, JOE)
        .put(GIN.symbol, GIN)
        .build();

    static final Supplier<String> validSymbols = () -> hashedStocks.keySet().stream().reduce("", (s1, s2) -> s1 + s2 + ", ");

    private final String symbol;
    private final StockType stockType;
    private final BigDecimal lastDividend;
    private final Optional<BigDecimal> fixedDividend;
    private final BigDecimal parValue;

    private Stock(String symbol,
                  StockType stockType,
                  BigDecimal lastDividend,
                  BigDecimal parValue) {
        this(symbol, stockType, lastDividend, Optional.<BigDecimal>empty(), parValue);
    }

    private Stock(String symbol,
                  StockType stockType,
                  BigDecimal lastDividend,
                  Optional<BigDecimal> fixedDividend,
                  BigDecimal parValue) {

        this.symbol = symbol;
        this.stockType = stockType;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }

    public static Optional<Stock> fromString(String value) {
        final String key = Strings.nullToEmpty(value).toUpperCase().trim();
        final Stock result = hashedStocks.get(key);
        return result == null ? Optional.<Stock>empty() : Optional.of(result);
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "Stock{" +
            "symbol='" + symbol + '\'' +
            ", stockType=" + stockType +
            ", lastDividend=" + lastDividend +
            ", fixedDividend=" + fixedDividend +
            ", parValue=" + parValue +
            '}';
    }

    enum StockType {
        COMMON,
        PREFERRED
    }
}

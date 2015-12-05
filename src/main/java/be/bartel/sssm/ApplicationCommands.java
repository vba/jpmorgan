package be.bartel.sssm;

import com.budhash.cliche.Command;
import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static java.lang.System.out;

@Singleton
final public class ApplicationCommands {

    private final DividendCalculationService dividendCalculationService;
    private final WeightedVolumeCalculationService weightedVolumeCalculationService;
    private final TradeStorageService tradeStorageService;

    @VisibleForTesting
    Stock currentStock;


    @Command
    public void stock(String stockSymbol) {
        final Optional<Stock> stockOption = Stock.fromString(stockSymbol);
        if (!stockOption.isPresent()) {
            out.println("Unknown stock symbol " + stockSymbol);
            out.println("Valid symbols are " + Stock.validSymbols.get());
        }
        else {
            out.println("Stock is set to " + stockOption.get().getSymbol());
            currentStock = stockOption.get();
        }
    }

    @Command
    public void dividend(String priceValue) {
        checkCurrentStock();
        final BigDecimal price = new BigDecimal(priceValue);
        out.println("Calculate dividend for current stock: " + currentStock.getSymbol());
        final BigDecimal result = this.dividendCalculationService.calculateDividend(currentStock, price);
        out.println("Result dividend is " + result);
    }

    @Command
    public void peratio(String priceValue) {
        checkCurrentStock();
        final BigDecimal price = new BigDecimal(priceValue);
        out.println("Calculate P/E for current stock: " + currentStock.getSymbol());
        final BigDecimal result = this.dividendCalculationService.calculatePERatio(currentStock, price);
        out.println("Result P/E ratio is " + result);
    }

    @Command
    public void sell(String timestamp, long shareQuantity, String tradedPrice) {
        checkCurrentStock();
        out.println("Sell for current stock "+ currentStock.getSymbol());
        tradeStorageService.sell(currentStock, Instant.parse(timestamp), shareQuantity, new BigDecimal(tradedPrice));
    }

    @Command
    public void sellNow(long shareQuantity, String tradedPrice) {
        checkCurrentStock();
        out.println("Sell now for current stock "+ currentStock.getSymbol());
        tradeStorageService.sell(currentStock, Instant.now(), shareQuantity, new BigDecimal(tradedPrice));
    }

    @Command
    public void trades() {
        final Set<Trade> trades = tradeStorageService.getAllTrades();
        if (trades.size() == 0) {
            out.println("Trades sequence is empty");
        }
        else {
            out.println("Trades sequence:");
            trades.forEach(out::println);
        }
    }

    @Command
    public void buyNow(long shareQuantity, String tradedPrice) {
        checkCurrentStock();
        out.println("Buy now for current stock "+ currentStock.getSymbol());
        tradeStorageService.buy(currentStock, Instant.now(), shareQuantity, new BigDecimal(tradedPrice));
    }

    @Command
    public void buy(String timestamp, long shareQuantity, String tradedPrice) {
        checkCurrentStock();
        out.println("Buy for current stock "+ currentStock.getSymbol());
        tradeStorageService.buy(currentStock, Instant.parse(timestamp), shareQuantity, new BigDecimal(tradedPrice));
    }

    private void checkCurrentStock() {
        if (currentStock == null) {
            throw new IllegalStateException("Stock isn't set, please use stock command before");
        }
    }

    @Inject
    public ApplicationCommands(DividendCalculationService dividendCalculationService,
                               WeightedVolumeCalculationService weightedVolumeCalculationService,
                               TradeStorageService tradeStorageService) {

        this.dividendCalculationService = dividendCalculationService;
        this.weightedVolumeCalculationService = weightedVolumeCalculationService;
        this.tradeStorageService = tradeStorageService;
    }

}

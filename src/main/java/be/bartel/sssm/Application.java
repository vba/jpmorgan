package be.bartel.sssm;

import com.budhash.cliche.Command;
import com.budhash.cliche.Shell;
import com.budhash.cliche.ShellFactory;
import com.google.common.annotations.VisibleForTesting;
import com.google.inject.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import static java.lang.System.out;

@Singleton
final public class Application {

    private final DividendCalculationService dividendCalculationService;
    private final WeightedVolumeCalculationService weightedVolumeCalculationService;
    private final TradeStorageService tradeStorageService;

    @VisibleForTesting
    Stock currentStock;


    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(new GuiceModule());
        Application application = injector.getInstance(Application.class);
        final Shell consoleShell = ShellFactory
            .createConsoleShell("SSSM welcomes you ", "", application);

        consoleShell.commandLoop();
    }

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
        final BigDecimal price = new BigDecimal(priceValue);
        out.println("Calculate dividend for current stock: " + currentStock.getSymbol());
        final BigDecimal result = this.dividendCalculationService.calculateDividend(currentStock, price);
        out.println("Result dividend is " + result);
    }

    @Command
    public void peratio(String priceValue) {
        final BigDecimal price = new BigDecimal(priceValue);
        out.println("Calculate dividend for current stock: " + currentStock.getSymbol());
        final BigDecimal result = this.dividendCalculationService.calculatePERatio(currentStock, price);
        out.println("Result P/E ratio is " + result);
    }

    @Inject
    public Application(DividendCalculationService dividendCalculationService,
                       WeightedVolumeCalculationService weightedVolumeCalculationService,
                       TradeStorageService tradeStorageService) {

        this.dividendCalculationService = dividendCalculationService;
        this.weightedVolumeCalculationService = weightedVolumeCalculationService;
        this.tradeStorageService = tradeStorageService;
    }
    final static class GuiceModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(DividendCalculationService.class).to(DividendCalculationServiceImpl.class);
            bind(TradeStorageService.class).to(TradeStorageServiceImpl.class);
            bind(WeightedVolumeCalculationService.class).to(WeightedVolumeCalculationServiceImpl.class);
        }
    }
}

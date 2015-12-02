package be.bartel.sssm;

import com.budhash.cliche.Command;
import com.budhash.cliche.Shell;
import com.budhash.cliche.ShellFactory;
import com.google.inject.*;

import java.io.IOException;
import java.util.Optional;

@Singleton
final public class Application {

    private final DividendCalculationService dividendCalculationService;
    private final WeightedVolumeCalculationService weightedVolumeCalculationService;
    private final TradeStorageService tradeStorageService;
    private Stock currentStock;

    @Inject
    public Application(DividendCalculationService dividendCalculationService,
                       WeightedVolumeCalculationService weightedVolumeCalculationService,
                       TradeStorageService tradeStorageService) {

        this.dividendCalculationService = dividendCalculationService;
        this.weightedVolumeCalculationService = weightedVolumeCalculationService;
        this.tradeStorageService = tradeStorageService;
    }

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
            System.out.println("Unknown stock symbol " + stockSymbol);
            System.out.println("Valid symbols are " + Stock.validSymbols.get());
        }
        else {
            System.out.println("Stock is set to " + stockOption.get().getSymbol());
            currentStock = stockOption.get();
        }
    }

    final static class GuiceModule extends AbstractModule
    {
        @Override
        protected void configure() {
            bind(DividendCalculationService.class).to(DividendCalculationServiceImpl.class);
            bind(TradeStorageService.class).to(TradeStorageServiceImpl.class);
            bind(WeightedVolumeCalculationService.class).to(WeightedVolumeCalculationServiceImpl.class);
        }
    }
}

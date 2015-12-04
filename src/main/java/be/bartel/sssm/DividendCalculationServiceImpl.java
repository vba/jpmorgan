package be.bartel.sssm;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;
import static java.math.RoundingMode.HALF_EVEN;

public class DividendCalculationServiceImpl implements DividendCalculationService {

    private final int SCALE = 2;

    @Override
    public BigDecimal calculateDividend(Stock stock, BigDecimal price) {
        checkCalculationArguments(stock, price);

        return Stock.StockType.PREFERRED.equals(stock.getStockType())
            ? stock.getFixedDividend().get().multiply(stock.getParValue()).divide(price, SCALE, HALF_EVEN)
            : stock.getLastDividend().divide(price, SCALE, HALF_EVEN);
    }

    @Override
    public BigDecimal calculatePERatio(Stock stock, BigDecimal price) {
        checkCalculationArguments(stock, price);
        return price.divide(stock.getLastDividend(), SCALE, HALF_EVEN);
    }

    private void checkCalculationArguments(Stock stock, BigDecimal price) {
        checkArgument(stock != null, "Stock cannot be a null, please set it first");
        checkArgument(price != null, "Price cannot be a null");
    }
}

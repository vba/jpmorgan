package be.bartel.sssm;

import java.math.BigDecimal;

public interface DividendCalculationService {
    BigDecimal calculateDividend(Stock stock, BigDecimal price);

    BigDecimal calculatePERatio(Stock stock, BigDecimal price);
}

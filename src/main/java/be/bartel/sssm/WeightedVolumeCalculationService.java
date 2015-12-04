package be.bartel.sssm;

import java.math.BigDecimal;

public interface WeightedVolumeCalculationService {
    BigDecimal calculate(Stock stock);
}

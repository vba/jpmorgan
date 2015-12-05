package be.bartel.sssm;

import java.math.BigDecimal;

public interface NthRootHelper {
    BigDecimal calculate(int n, BigDecimal a);

    BigDecimal calculate(BigDecimal root, BigDecimal number, BigDecimal p);
}

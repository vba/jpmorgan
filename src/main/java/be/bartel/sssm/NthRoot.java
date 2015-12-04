package be.bartel.sssm;

import com.google.common.annotations.VisibleForTesting;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;
import static java.math.RoundingMode.HALF_EVEN;

final class NthRoot {

    @VisibleForTesting
    static final int SCALE = 2;

    public static BigDecimal calculate(int n, BigDecimal a) {
        return calculate(new BigDecimal(n), a, new BigDecimal("0.001"));
    }

    public static BigDecimal calculate(BigDecimal root, BigDecimal number, BigDecimal p) {
        checkArgument(number != null, "A cannot be number null");
        checkArgument(p != null, "p cannot be number null");
        checkArgument(number.compareTo(BigDecimal.ZERO) >= 0, "Only positive real numbers are accepted");

        if (number.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        BigDecimal prev = number;
        BigDecimal curr = number.divide(root, SCALE, HALF_EVEN);
        final BigDecimal temp = root.subtract(new BigDecimal("1.0"));

        while(curr.subtract(prev).abs().compareTo(p) > 0) {
            prev = curr;
            curr = curr.multiply(temp)
                .add(number.divide(curr.pow(temp.intValue()), SCALE, HALF_EVEN))
                .divide(root, SCALE, HALF_EVEN);
        }
        return curr;
    }
}

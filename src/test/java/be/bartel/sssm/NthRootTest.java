package be.bartel.sssm;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Theories.class)
public class NthRootTest {

    @Test(expected = IllegalArgumentException.class)
    public void It_should_crash_with_a_negative_number() throws Exception {
        // Given
        final int root = 2;
        final BigDecimal number = new BigDecimal(-1);

        // When
        NthRoot.calculate(root, number);

        // Then
    }

    @Test
    public void It_should_return_0_for_0() throws Exception {
        // Given
        final int root = 2;
        final BigDecimal number = new BigDecimal(0);
        BigDecimal actual;

        // When
        actual = NthRoot.calculate(root, number);

        // Then
        assertThat(actual).isZero().withFailMessage("A nth root of 0 is 0");
    }

    @Theory(nullsAccepted = false)
    public void It_should_calculate_root_as_expected(RootDto dto) throws Exception {
        // Given
        final int root = dto.getBase();
        final BigDecimal number = dto.getNumber();
        BigDecimal actual;

        // When
        actual = NthRoot.calculate(root, number);

        // Then
        assertThat(actual).isEqualTo(dto.getResult().setScale(NthRoot.SCALE))
            .withFailMessage("A square root of "+dto.getNumber()+" is "+ dto.getResult());
    }

    @DataPoints
    public static RootDto[] data() {
        return new RootDto[] {
            new RootDto(2, new BigDecimal(16), new BigDecimal(4)),
            new RootDto(3, new BigDecimal(125), new BigDecimal(5)),
            new RootDto(3, new BigDecimal(27), new BigDecimal(3)),
            new RootDto(4, new BigDecimal("39.0625"), new BigDecimal(2.5)),
            new RootDto(10, new BigDecimal("3.402584077894253e+038"), new BigDecimal(7131.5)),
            new RootDto(10, new BigDecimal(1024), new BigDecimal(2))
        };
    }

    private final static class RootDto {
        private final int base;
        private final BigDecimal number;
        private final BigDecimal result;

        public RootDto(int base, BigDecimal number, BigDecimal result) {
            this.base = base;
            this.number = number;
            this.result = result;
        }

        public int getBase() {
            return base;
        }

        public BigDecimal getNumber() {
            return number;
        }

        public BigDecimal getResult() {
            return result;
        }
    }
}
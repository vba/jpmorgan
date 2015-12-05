package be.bartel.sssm;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static be.bartel.sssm.Stock.*;
import static be.bartel.sssm.Stock.POP;
import static be.bartel.sssm.Stock.TEA;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Theories.class)
public class DividendCalculationServiceImplTest {

    @Theory(nullsAccepted = false)
    public void It_should_calculate_dividend_upon_formulas(DividendDto data) throws Exception {
        // Given
        final DividendCalculationServiceImpl sut = makeSut();
        BigDecimal actual;

        // When
        actual = sut.calculateDividend(data.getStock(), data.getPrice());

        // Then
        assertThat(actual).isEqualTo(data.getResult());
    }

    @Theory(nullsAccepted = false)
    public void It_should_calculate_pe_ratio_upon_formulas(PERatioDto data) throws Exception {
        // Given
        final DividendCalculationServiceImpl sut = makeSut();
        BigDecimal actual;

        // When
        actual = sut.calculatePERatio(data.getStock(), data.getPrice());

        // Then
        assertThat(actual).isEqualTo(data.getResult());
    }

    private DividendCalculationServiceImpl makeSut() {
        return new DividendCalculationServiceImpl();
    }

    @DataPoints
    public static DividendDto[] dividendData() {
        return new DividendDto[] {
            new DividendDto(TEA, new BigDecimal("20.45"), new BigDecimal("0.00")),
            new DividendDto(POP, new BigDecimal("99.54"), new BigDecimal("0.08")),
            new DividendDto(ALE, new BigDecimal("1067.56"), new BigDecimal("0.02")),
            new DividendDto(GIN, new BigDecimal("1573.89"), new BigDecimal("0.13")),
            new DividendDto(JOE, new BigDecimal("456.99"), new BigDecimal("0.03")),
        };
    }
    @DataPoints
    public static PERatioDto[] peratioData() {
        return new PERatioDto[] {
            new PERatioDto(POP, new BigDecimal("99.54"), new BigDecimal("12.44")),
            new PERatioDto(ALE, new BigDecimal("1067.56"), new BigDecimal("46.42")),
            new PERatioDto(GIN, new BigDecimal("1573.89"), new BigDecimal("196.74")),
            new PERatioDto(JOE, new BigDecimal("456.99"), new BigDecimal("35.15")),
        };
    }

    private static class DividendDto extends Dto {
        public DividendDto(Stock stock, BigDecimal price, BigDecimal result) {
            super(stock, price, result);
        }
    }

    private static class PERatioDto extends Dto {
        public PERatioDto(Stock stock, BigDecimal price, BigDecimal result) {
            super(stock, price, result);
        }
    }

    private static abstract class Dto {
        private final Stock stock;
        private final BigDecimal price;
        private final BigDecimal result;

        public Dto(Stock stock, BigDecimal price, BigDecimal result) {
            this.stock = stock;
            this.price = price;
            this.result = result;
        }

        public Stock getStock() {
            return stock;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public BigDecimal getResult() {
            return result;
        }
    }


}
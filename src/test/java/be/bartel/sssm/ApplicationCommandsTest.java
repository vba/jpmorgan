package be.bartel.sssm;

import org.junit.Test;
import org.mockito.internal.verification.Times;

import java.math.BigDecimal;

import static com.google.common.base.Optional.fromNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ApplicationCommandsTest {

    @Test
    public void It_should_set_stock_as_expected() throws Exception {
        // Given
        final ApplicationCommands sut = makeSut(null, null, null, null);

        // When
        sut.stock(Stock.ALE.getSymbol());

        // Then
        assertThat(sut.currentStock).isEqualTo(Stock.ALE)
            .withFailMessage("Stock should be set to ALE");
    }

    @Test
    public void It_should_not_set_stock_when_stock_is_unknown() throws Exception {
        // Given
        final ApplicationCommands sut = makeSut(null, null, null, null);

        // When
        sut.stock("nonsense21");

        // Then
        assertThat(sut.currentStock).withFailMessage("When stock isn't set current value is null").isNull();
    }

    @Test
    public void It_should_call_dividend_as_expected() throws Exception {
        // Given
        final Stock stock = Stock.ALE;
        final String priceValue = "25";
        final BigDecimal price = new BigDecimal(priceValue);
        final DividendCalculationService dividendCalculationService = mock(DividendCalculationService.class, RETURNS_SMART_NULLS);
        final BigDecimal actual = new BigDecimal("1");
        final ApplicationCommands sut = makeSut(dividendCalculationService, null, null, null);

        sut.currentStock = stock;
        when(dividendCalculationService.calculateDividend(stock, price)).thenReturn(actual);

        // When
        sut.dividend(priceValue);

        // Then
        verify(dividendCalculationService, new Times(1)).calculateDividend(stock, price);
    }
    
    private ApplicationCommands makeSut(final DividendCalculationService dividendCalculationService,
                                        final WeightedVolumeCalculationService weightedVolumeCalculationService,
                                        final GBCECalculationService gbceCalculationService,
                                        final TradeStorageService tradeStorageService) {
        
        return new ApplicationCommands(
            fromNullable(dividendCalculationService).or(mock(DividendCalculationService.class, RETURNS_SMART_NULLS)),
            fromNullable(weightedVolumeCalculationService).or(mock(WeightedVolumeCalculationService.class, RETURNS_SMART_NULLS)),
            fromNullable(gbceCalculationService).or(mock(GBCECalculationService.class, RETURNS_SMART_NULLS)),
            fromNullable(tradeStorageService).or(mock(TradeStorageService.class, RETURNS_SMART_NULLS))
        );
    }
}

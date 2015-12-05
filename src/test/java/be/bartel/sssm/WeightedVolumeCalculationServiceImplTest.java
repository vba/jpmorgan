package be.bartel.sssm;

import com.flextrade.jfixture.JFixture;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Set;

import static be.bartel.sssm.Stock.ALE;
import static be.bartel.sssm.Trade.createBuyTrade;
import static com.google.common.base.Optional.fromNullable;
import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WeightedVolumeCalculationServiceImplTest {

    private JFixture fixture;

    @Before
    public void setUp() throws Exception {
        this.fixture = new JFixture();
        this.fixture.customise().circularDependencyBehaviour().omitSpecimen();
    }

    @Test
    public void It_should_return_0_when_no_trades_are_available() throws Exception {
        // Given
        final Stock stock = Stock.ALE;
        final TradeStorageService tradeStorageService = mock(TradeStorageService.class, RETURNS_SMART_NULLS);
        final WeightedVolumeCalculationServiceImpl sut = makeSut(tradeStorageService);
        BigDecimal actual;

        // When
        when(tradeStorageService.getLastTrades(stock)).thenReturn(ImmutableSet.of());
        actual = sut.calculate(stock);

        // Then
        assertThat(actual).isEqualTo(BigDecimal.ZERO)
            .withFailMessage("When no last trades are supplied then calculate should return 0");
    }

    @Test
    public void It_should_make_banal_calculation_as_expected() throws Exception {
        // Given
        final Stock stock = Stock.ALE;
        final Set<Trade> trades = ImmutableSet.of(
            createBuyTrade(ALE, now(), 2, new BigDecimal(10)),
            createBuyTrade(ALE, now(), 3, new BigDecimal(20))
        );
        final BigDecimal expected = new BigDecimal("16.00"); // ((10 * 2) + (20 * 3)) / 2 + 3
        final TradeStorageService tradeStorageService = mock(TradeStorageService.class, RETURNS_SMART_NULLS);
        final WeightedVolumeCalculationServiceImpl sut = makeSut(tradeStorageService);
        BigDecimal actual;

        // When
        when(tradeStorageService.getLastTrades(stock)).thenReturn(trades);
        actual = sut.calculate(stock);

        // Then
        assertThat(actual).isEqualTo(expected)
            .withFailMessage("Actual result should correspond to formula's based expected value");
    }

    public WeightedVolumeCalculationServiceImpl makeSut(TradeStorageService tradeStorageService) {
        return new WeightedVolumeCalculationServiceImpl(
            fromNullable(tradeStorageService).or(mock(TradeStorageService.class, RETURNS_SMART_NULLS))
        );
    }
}
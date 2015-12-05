package be.bartel.sssm;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.mockito.internal.verification.Times;

import java.math.BigDecimal;

import static com.google.common.base.Optional.fromNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GBCECalculationServiceImplTest {


    @Test
    public void It_should_calculate_GBCE_as_expected() throws Exception {
        // Given
        final TradeStorageService tradeStorageService = mock(TradeStorageService.class, RETURNS_SMART_NULLS);
        final NthRootHelper nthRootHelper = mock(NthRootHelper.class, RETURNS_SMART_NULLS);
        final GBCECalculationServiceImpl sut = makeSut(tradeStorageService, nthRootHelper);
        BigDecimal actual;

        // When
        when(tradeStorageService.getAllTrades()).thenReturn(ImmutableSet.of());
        when(nthRootHelper.calculate(0, BigDecimal.ZERO)).thenReturn(BigDecimal.TEN);
        actual = sut.calculateAllSharedIndex();

        // Then
        assertThat(actual).isEqualTo(BigDecimal.TEN).withFailMessage("It should return ten");
        verify(tradeStorageService, new Times(1)).getAllTrades();
        verify(nthRootHelper, new Times(1)).calculate(0, BigDecimal.ZERO);
    }

    public GBCECalculationServiceImpl makeSut(TradeStorageService tradeStorageService, NthRootHelper nthRootHelper) {
        return new GBCECalculationServiceImpl(
            fromNullable(tradeStorageService).or(mock(TradeStorageService.class, RETURNS_SMART_NULLS)),
            fromNullable(nthRootHelper).or(mock(NthRootHelper.class, RETURNS_SMART_NULLS))
        );
    }
}
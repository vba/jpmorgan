package be.bartel.sssm;

import com.flextrade.jfixture.JFixture;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static be.bartel.sssm.Stock.*;
import static be.bartel.sssm.Trade.createBuyTrade;
import static org.assertj.core.api.Assertions.assertThat;

public class TradeStorageServiceImplIntegrationTest {

    private JFixture fixture;

    @Before
    public void setUp() throws Exception {
        this.fixture = new JFixture();
        this.fixture.customise().circularDependencyBehaviour().omitSpecimen();
    }

    @Test
    public void It_should_store_trades_and_retrieve_last_X_of_them() throws Exception {
        // Given
        final TradeStorageServiceImpl sut = makeSut();
        List<Trade> actual;

        // When
        actual = sut.getLastTrades(Stock.ALE);

        // Then
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getStock()).isEqualTo(Stock.ALE);
        assertThat(actual.get(1).getStock()).isEqualTo(Stock.ALE);
    }

    private ImmutableList<Trade> generateBuyTradesList() {
        final Instant now = Instant.now();
        final Instant before = Instant.now().minus(20, ChronoUnit.MINUTES);
        return ImmutableList.of(
            createBuyTrade(ALE, now, fixture.create(long.class), fixture.create(BigDecimal.class)),
            createBuyTrade(ALE, now, fixture.create(long.class), fixture.create(BigDecimal.class)),
            createBuyTrade(POP, now, fixture.create(long.class), fixture.create(BigDecimal.class)),
            createBuyTrade(ALE, before, fixture.create(long.class), fixture.create(BigDecimal.class)),
            createBuyTrade(ALE, before, fixture.create(long.class), fixture.create(BigDecimal.class))
        );
    }

    private TradeStorageServiceImpl makeSut() {
        return new TradeStorageServiceImpl(generateBuyTradesList());
    }
}
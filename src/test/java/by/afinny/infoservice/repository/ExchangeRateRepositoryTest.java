package by.afinny.infoservice.repository;

import by.afinny.infoservice.entity.ExchangeRate;
import by.afinny.infoservice.entity.constant.CurrencyCode;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/schema-h2.sql"}
)
@ActiveProfiles("test")
class ExchangeRateRepositoryTest {

    private final CurrencyCode CAD = CurrencyCode.CAD;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    private ExchangeRate exchangeRate;

    @BeforeAll
    void setUp() {
        exchangeRate = ExchangeRate.builder()
                .unit(1)
                .name("Canadian Dollar")
                .currencyCode(CAD)
                .sellingRate(new BigDecimal("100.0000"))
                .buyingRate(new BigDecimal("100.0000"))
                .updateAt(Instant.now())
                .cross(false)
                .isoCode("124")
                .sign("C$")
                .build();
    }

    @AfterEach
    void cleanUp() {
        exchangeRateRepository.deleteAll();
    }

    @Test
    @DisplayName("If currency not exists then return empty")
    void findByCurrencyCode_ifCurrencyNotExists_thenReturnEmpty() {
        //ACT
        Optional<ExchangeRate> result = exchangeRateRepository.findByCurrencyCode(CAD);

        //VERIFY
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("If currency exists then return exchange rate")
    void findByCurrencyCode_ifCurrencyExists_thenReturnExchangeRate() {
        //ARRANGE
        UUID id = exchangeRateRepository.save(exchangeRate).getId();
        exchangeRate.setId(id);

        //ACT
        ExchangeRate result = getExchangeRate(CAD);

        //VERIFY
        verifyExchangeRate(this.exchangeRate, result);
    }

    private void verifyExchangeRate(ExchangeRate expected, ExchangeRate actual) {
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actual.getId())
                    .isEqualTo(expected.getId());
            softAssertions.assertThat(actual.getUpdateAt())
                    .isCloseTo(expected.getUpdateAt(), new TemporalUnitWithinOffset(1, ChronoUnit.SECONDS));
            softAssertions.assertThat(actual.getCurrencyCode())
                    .isEqualTo(expected.getCurrencyCode());
            softAssertions.assertThat(actual.getIsoCode())
                    .isEqualTo(expected.getIsoCode());
            softAssertions.assertThat(actual.getName())
                    .isEqualTo(expected.getName());
            softAssertions.assertThat(actual.getSign())
                    .isEqualTo(expected.getSign());
            softAssertions.assertThat(actual.getUnit())
                    .isEqualTo(expected.getUnit());
            softAssertions.assertThat(actual.getBuyingRate())
                    .isEqualTo(expected.getBuyingRate());
            softAssertions.assertThat(actual.getSellingRate())
                    .isEqualTo(expected.getSellingRate());
            softAssertions.assertThat(actual.getCross())
                    .isEqualTo(expected.getCross());
        });
    }

    private ExchangeRate getExchangeRate(CurrencyCode currencyCode) {
        return exchangeRateRepository.findByCurrencyCode(currencyCode)
                .orElseThrow(() -> new EntityNotFoundException("exchange rate of " + currencyCode + "not found"));
    }
}
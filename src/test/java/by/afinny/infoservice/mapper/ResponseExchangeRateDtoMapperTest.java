package by.afinny.infoservice.mapper;

import by.afinny.infoservice.dto.ExchangeRateDto;
import by.afinny.infoservice.entity.ExchangeRate;
import by.afinny.infoservice.entity.constant.CurrencyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ResponseExchangeRateDtoMapperTest {

    @InjectMocks
    private ResponseExchangeRateMapperImpl exchangeRateMapper;

    private ExchangeRate exchangeRate;

    @BeforeAll
    void setUp() {
        exchangeRate = ExchangeRate.builder()
                .updateAt(Instant.now())
                .buyingRate(new BigDecimal(100))
                .sellingRate(new BigDecimal(100))
                .currencyCode(CurrencyCode.USD)
                .name("dollar")
                .unit(1).build();
    }

    @Test
    @DisplayName("verify ExchangeRateDto fields settings")
    void toExchangeRateDto_shouldReturnExchangeRateDto() {
        ExchangeRateDto exchangeRateDto = exchangeRateMapper.toExchangeRateDto(exchangeRate);
        verifyExchangeRateDto(exchangeRateDto);
    }

    private void verifyExchangeRateDto(ExchangeRateDto exchangeRateDto) {
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(exchangeRateDto.getCurrencyCode())
                    .isEqualTo(exchangeRate.getCurrencyCode());
            softAssertions.assertThat(exchangeRateDto.getBuyingRate())
                    .isEqualTo(exchangeRate.getBuyingRate());
            softAssertions.assertThat(exchangeRateDto.getSellingRate())
                    .isEqualTo(exchangeRate.getSellingRate());
            softAssertions.assertThat(exchangeRateDto.getUnit())
                    .isEqualTo(exchangeRate.getUnit());
        });
    }
}
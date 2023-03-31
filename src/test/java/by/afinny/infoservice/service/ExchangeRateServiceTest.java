package by.afinny.infoservice.service;

import by.afinny.infoservice.dto.ExchangeRateDto;
import by.afinny.infoservice.entity.ExchangeRate;
import by.afinny.infoservice.entity.constant.CurrencyCode;
import by.afinny.infoservice.mapper.ResponseExchangeRateMapper;
import by.afinny.infoservice.mapper.ResponseExchangeRateMapperImpl;
import by.afinny.infoservice.repository.ExchangeRateRepository;
import by.afinny.infoservice.service.impl.ExchangeRateServiceImpl;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ExchangeRateServiceTest {

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;
    @Mock
    private ExchangeRateRepository exchangeRateRepository;
    @Spy
    private ResponseExchangeRateMapper responseExchangeRateMapper = new ResponseExchangeRateMapperImpl();
    private ExchangeRate exchangeRate;

    @BeforeEach
    void setUp() {
        exchangeRate = ExchangeRate.builder()
                .updateAt(Instant.now())
                .currencyCode(CurrencyCode.USD)
                .buyingRate(new BigDecimal(85))
                .sellingRate(new BigDecimal(80))
                .unit(1)
                .sign("$")
                .name("dollar")
                .build();

    }


    @Test
    @DisplayName("if success then return exchangeRateDto")
    void getExchangeRateByCurrencyCode_shouldReturnExchangeRateDto() {
        //ARRANGE
        when(exchangeRateRepository.findByCurrencyCode(any(CurrencyCode.class))).thenReturn(Optional.of(exchangeRate));

        //ACT
        ExchangeRateDto exchangeRateDto = exchangeRateService.getExchangeRateByCurrencyCode(CurrencyCode.USD);

        //VERIFY
        assertThat(exchangeRateDto).isNotNull();

    }

    @Test
    @DisplayName("If Entity not found then throw Entity Not Found")
    void getExchangeRateByCurrencyCode_ifNotSuccess_thenThrow() {
        //ARRANGE
        when(exchangeRateRepository.findByCurrencyCode(any(CurrencyCode.class))).thenThrow(EntityNotFoundException.class);

        //ACT
        ThrowingCallable getExchangeRateByCurrencyCode = () -> exchangeRateService.getExchangeRateByCurrencyCode(CurrencyCode.USD);

        //VERIFY
        assertThatThrownBy(getExchangeRateByCurrencyCode)
                .isInstanceOf(EntityNotFoundException.class);
    }

}
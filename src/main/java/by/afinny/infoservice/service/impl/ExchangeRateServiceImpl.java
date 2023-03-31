package by.afinny.infoservice.service.impl;


import by.afinny.infoservice.dto.ExchangeRateDto;
import by.afinny.infoservice.entity.ExchangeRate;
import by.afinny.infoservice.entity.constant.CurrencyCode;
import by.afinny.infoservice.mapper.ResponseExchangeRateMapper;
import by.afinny.infoservice.repository.ExchangeRateRepository;
import by.afinny.infoservice.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ResponseExchangeRateMapper responseExchangeRateMapper;

    @Override
    public ExchangeRateDto getExchangeRateByCurrencyCode(CurrencyCode currencyCode) {
        log.info("getExchangeRateByCurrencyCode() method invoked");
        ExchangeRate exchangeRate = getExchangeRate(currencyCode);
        return responseExchangeRateMapper.toExchangeRateDto(exchangeRate);
    }

    private ExchangeRate getExchangeRate(CurrencyCode currencyCode) {
        return exchangeRateRepository.findByCurrencyCode(currencyCode)
                .orElseThrow(() -> new EntityNotFoundException("exchange rate of " + currencyCode + "not found"));
    }
}

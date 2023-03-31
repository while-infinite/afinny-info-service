package by.afinny.infoservice.service;


import by.afinny.infoservice.dto.ExchangeRateDto;
import by.afinny.infoservice.entity.constant.CurrencyCode;

public interface ExchangeRateService {

    ExchangeRateDto getExchangeRateByCurrencyCode(CurrencyCode currencyCode);
}

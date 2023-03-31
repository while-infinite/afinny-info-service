package by.afinny.infoservice.controller;

import by.afinny.infoservice.dto.ExchangeRateDto;
import by.afinny.infoservice.entity.constant.CurrencyCode;
import by.afinny.infoservice.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("auth/rates")
    public ResponseEntity<List<ExchangeRateDto>> getExchangeRatesByCurrencyCode(@RequestParam CurrencyCode currencyCodeFrom,
                                                                                @RequestParam CurrencyCode currencyCodeTo) {
        ExchangeRateDto from = exchangeRateService.getExchangeRateByCurrencyCode(currencyCodeFrom);
        ExchangeRateDto to = exchangeRateService.getExchangeRateByCurrencyCode(currencyCodeTo);
        return ResponseEntity.ok(List.of(from, to));
    }
}

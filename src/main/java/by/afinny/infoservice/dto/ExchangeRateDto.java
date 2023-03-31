package by.afinny.infoservice.dto;

import by.afinny.infoservice.entity.constant.CurrencyCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
@ToString
public class ExchangeRateDto {

    CurrencyCode currencyCode;
    BigDecimal buyingRate;
    BigDecimal sellingRate;
    Integer unit;
}

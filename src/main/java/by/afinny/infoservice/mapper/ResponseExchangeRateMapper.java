package by.afinny.infoservice.mapper;

import by.afinny.infoservice.dto.ExchangeRateDto;
import by.afinny.infoservice.entity.ExchangeRate;
import org.mapstruct.Mapper;

@Mapper
public interface ResponseExchangeRateMapper {

    ExchangeRateDto toExchangeRateDto(ExchangeRate exchangeRate);
}

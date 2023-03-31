package by.afinny.infoservice.repository;


import by.afinny.infoservice.entity.ExchangeRate;
import by.afinny.infoservice.entity.constant.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {

    Optional<ExchangeRate> findByCurrencyCode(CurrencyCode currencyCode);
}
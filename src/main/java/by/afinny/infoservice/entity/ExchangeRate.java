package by.afinny.infoservice.entity;

import by.afinny.infoservice.entity.constant.CurrencyCode;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = ExchangeRate.TABLE_NAME)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
@ToString
public class ExchangeRate {

    public static final String TABLE_NAME = "exchange_rate";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "update_at")
    private Instant updateAt;

    @Column(name = "currency_code", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @Column(name = "iso_code", length = 3, nullable = false)
    private String isoCode;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "sign", length = 10, nullable = false)
    private String sign;

    @Column(name = "unit", nullable = false)
    private Integer unit;

    @Column(name = "buying_rate", precision = 10, scale = 4, nullable = false)
    private BigDecimal buyingRate;

    @Column(name = "selling_rate", precision = 10, scale = 4, nullable = false)
    private BigDecimal sellingRate;

    @Column(name = "is_cross", nullable = false)
    private Boolean cross;
}
